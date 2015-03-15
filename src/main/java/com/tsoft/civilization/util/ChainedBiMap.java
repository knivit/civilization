package com.tsoft.civilization.util;

import java.util.HashMap;
import java.util.Map;

public class ChainedBiMap<K, V> {
    private Map<K, V> map = new HashMap<K, V>();

    public ChainedBiMap<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }
}
