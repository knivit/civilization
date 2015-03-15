package com.tsoft.civilization.L10n;

import java.util.ArrayList;

public class L10nChainedArrayList<V> extends ArrayList<V> {
    public L10nChainedArrayList set(V value) {
        add(value);
        return this;
    }
}
