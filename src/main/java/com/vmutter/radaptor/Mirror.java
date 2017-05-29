package com.vmutter.radaptor;

import com.vmutter.radaptor.exception.RadaptorException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mirror {

    private List<Pair<String, Field>> declaredFields = new ArrayList<>();

    public Mirror(final Class<?> clazz) {
        loadClass(clazz);
    }

    private void loadClass(final Class<?> clazz) {
        for (final Field field : clazz.getDeclaredFields()) {
            declaredFields.add(new Pair<>(field.getName(), field));
        }

        if (clazz.getSuperclass() != null) {
            loadClass(clazz.getSuperclass());
        }
    }

    // TODO put an local variable to avoid all searchs
    public Field getDeclaredField(final String name) {
        return declaredFields.stream().filter(p -> p.getKey().equals(name))
            .findFirst().map(Pair::getValue).orElse(null);
    }

    public List<String> getDeclaredFieldsName() {
        return declaredFields.stream().map(Pair::getKey).collect(Collectors.toList());
    }

    public static Object get(final Field field, final Object object, final Class<?> type) {
        field.setAccessible(true);
        try {
            Object value = field.get(object);
            value = convert(value, type);
            return value;
        } catch (final IllegalAccessException e) {
            throw new RadaptorException(e);
        } finally {
            field.setAccessible(false);
        }
    }

    public static void set(final Field field, final Object object, final Object value) {
        field.setAccessible(true);
        try {
            final Object convertedValue = convert(value, field.getType());
            field.set(object, convertedValue == null ? value : convertedValue);
        } catch (final IllegalAccessException e) {
            throw new RadaptorException(e);
        }
        field.setAccessible(false);
    }

    private static Object convert(final Object value, final Class<?> type) {
        try {
            if (!value.getClass().equals(type)) {
                final Method m = Converter.class.getDeclaredMethod("convert", value.getClass());
                return m.invoke(null, value);
            }
            return value;
        } catch (final IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RadaptorException(e);
        }
    }
}
