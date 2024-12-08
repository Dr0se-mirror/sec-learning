package com.drose.CC1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        获取runtime类的内存中的class对象，也就是Class
        Class c=Runtime.class;
//        通过原型获取方法
        Method method=c.getMethod("exec",String.class);
//        开启权限
        method.setAccessible(true);
//        获取对象
        Runtime rt = Runtime.getRuntime();
        method.invoke(rt,"calc");
    }
}
