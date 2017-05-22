package com.vmutter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mirror {

    private List<Pair<String, Field>> declaredFields = new ArrayList<>();

    public Mirror(final Class<?> clazz) {
        for (final Field field : clazz.getDeclaredFields()) {
            declaredFields.add(new Pair<>(field.getName(), field));
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
}
