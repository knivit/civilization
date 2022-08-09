package com.tsoft.civilization.util.l10n;

import java.util.HashMap;
import java.util.Map;

public class L10nMap<K, V> {

    private final Map<K, V> map = new HashMap<>();

    public V get(K key) {
        return map.get(key);
    }

    public L10nMap<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }
}
