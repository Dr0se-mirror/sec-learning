package com.drose;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.comparators.TransformingComparator;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Dr0se {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name = "Dr0se";

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        System.out.println(PropertyUtils.getProperty(new Dr0se(),"name"));
    }


}
