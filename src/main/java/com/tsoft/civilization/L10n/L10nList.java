package com.tsoft.civilization.L10n;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class L10nList {

    private final List<L10n> list = new ArrayList<>();

    public L10n get(int index) {
        return list.get(index);
    }

    public L10nList add(L10n value) {
        list.add(value);
        return this;
    }

    public int size() {
        return list.size();
    }

    public Stream<L10n> stream() {
        return list.stream();
    }
}
