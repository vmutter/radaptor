package com.vmutter.radaptor;

import com.vmutter.radaptor.annotation.Alias;

import java.lang.reflect.Field;

public class Handbook {

    public static String getFieldName(final Field field) {
        return field.isAnnotationPresent(Alias.class)
            ? field.getAnnotation(Alias.class).value() : field.getName();
    }

}
