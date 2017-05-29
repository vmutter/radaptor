package com.vmutter.radaptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Radaptor {

    private Radaptor() {
    }

    public static <T> T adapt(final Object fromObject, final Class<T> clazz) throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException, InstantiationException {

        final Constructor<T> constructor = clazz.getDeclaredConstructor();
        final T toObject = constructor.newInstance();

        final Mirror to = new Mirror(clazz);
        final Mirror from = new Mirror(fromObject.getClass());

        for (final String name : to.getDeclaredFieldsName()) {
            final Field toField = to.getDeclaredField(name);
            final Field fromField = from.getDeclaredField(Handbook.getFieldName(toField));
            if (fromField != null) {
                Mirror.set(toField, toObject, Mirror.get(fromField, fromObject, toField.getType()));
            }
        }
        return clazz.cast(toObject);
    }

    public static <T> T build(final Object fromObject, final Class<T> clazz) throws NoSuchMethodException,
        InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        final Method method = clazz.getDeclaredMethod("builder");
        final Object toObjectBuilder = method.invoke(null);

        final Mirror to = new Mirror(clazz);
        final Mirror from = new Mirror(fromObject.getClass());

        for (final String name : to.getDeclaredFieldsName()) {
            final Field toField = to.getDeclaredField(name);
            final Field fromField = from.getDeclaredField(Handbook.getFieldName(toField));
            if (fromField != null) {
                final Method m = toObjectBuilder.getClass().getDeclaredMethod(name, toField.getType());
                m.invoke(toObjectBuilder, Mirror.get(fromField, fromObject, toField.getType()));
            }
        }

        return clazz.cast(toObjectBuilder.getClass().getMethod("build").invoke(toObjectBuilder));
    }
}
