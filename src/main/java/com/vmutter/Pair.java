package com.vmutter;

import lombok.Data;

@Data
public class Pair<K, V> {
    private K key;

    private V value;

    Pair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }
}
