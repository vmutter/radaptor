package com.vmutter;

import com.vmutter.annotation.Alias;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Radaptor {

    private Radaptor() {
    }

    public static <T> T adapt(final Object fromObject, final Class<T> clazz) throws IllegalAccessException,
        InvocationTargetException, InstantiationException, NoSuchFieldException, NoSuchMethodException {

        final Constructor<T> constructor = clazz.getDeclaredConstructor();
        final T toObject = constructor.newInstance();

        final Mirror to = new Mirror(clazz);
        final Mirror from = new Mirror(fromObject.getClass());

        for (final String name : to.getDeclaredFieldsName()) {
            final Field toField = to.getDeclaredField(name);
            final Alias alias = toField.isAnnotationPresent(Alias.class)
                ? toField.getAnnotation(Alias.class) : null;
            if (from.getDeclaredFieldsName().contains(name)
                || (alias != null && from.getDeclaredFieldsName().contains(alias.value()))) {
                Field fromField = null;
                if (alias == null) {
                    fromField = from.getDeclaredField(name);
                } else {
                    fromField = from.getDeclaredField(alias.value());
                }
                toField.setAccessible(true);
                fromField.setAccessible(true);
                toField.set(toObject, fromField.get(fromObject));
                toField.setAccessible(false);
                fromField.setAccessible(false);
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
            final Alias alias = toField.isAnnotationPresent(Alias.class)
                ? toField.getAnnotation(Alias.class) : null;
            if (from.getDeclaredFieldsName().contains(name)
                || (alias != null && from.getDeclaredFieldsName().contains(alias.value()))) {
                final String methodName = alias != null ? alias.value() : name;
                final Field fromField = from.getDeclaredField(methodName);

                final Method m = toObjectBuilder.getClass().getDeclaredMethod(name,
                    from.getDeclaredField(methodName).getType());

                fromField.setAccessible(true);
                m.invoke(toObjectBuilder, fromField.get(fromObject));
                fromField.setAccessible(false);
            }
        }

        return clazz.cast(toObjectBuilder.getClass().getMethod("build").invoke(toObjectBuilder));
    }
}
