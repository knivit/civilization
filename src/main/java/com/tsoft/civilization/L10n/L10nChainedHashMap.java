package com.tsoft.civilization.L10n;

import java.util.HashMap;

public class L10nChainedHashMap<K, V> extends HashMap<K, V> {
    public L10nChainedHashMap set(K key, V value) {
        put(key, value);
        return this;
    }
}
