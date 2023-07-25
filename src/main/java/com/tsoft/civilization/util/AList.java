package com.tsoft.civilization.util;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Elements can't be null
 * Single-threaded
 */
public class AList<T> implements Iterable<T> {

    protected final List<T> list = new ArrayList<>();
    private boolean isUnmodifiable;

    public AList() { }

    public <E extends AList<T>> E create() {
        Constructor<E> constructor = (Constructor<E>) this.getClass().getConstructors()[0];

        try {
            return constructor.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public <E extends AList<T>> E copy() {
        return create().addAll((E)this);
    }

    public <E extends AList<T>> E unmodifiableCopy() {
        return copy().makeUnmodifiable();
    }

    public <E extends AList<T>> E clear() {
        checkIsUnmodifiable();
        list.clear();
        return (E)this;
    }

    public <E extends AList<T>> E add(T element) {
        Objects.requireNonNull(element, "The argument can't be null");

        checkIsUnmodifiable();
        list.add(element);
        return (E)this;
    }

    public <E extends AList<T>> E addAll(T ... elements) {
        Objects.requireNonNull(elements);

        for (T element : elements) {
            list.add(element);
        }

        return (E)this;
    }

    public <E extends AList<T>> E addAll(List<T> list) {
        Objects.requireNonNull(list, "The argument can't be null");

        checkIsUnmodifiable();
        this.list.addAll(list);
        return (E)this;
    }

    public <E extends AList<T>> E addAll(E list) {
        Objects.requireNonNull(list, "The argument can't be null");

        for (T element : list) {
            add(element);
        }
        return (E)this;
    }

    public <E extends AList<T>> E remove(T element) {
        Objects.requireNonNull(element, "The argument can't be null");

        checkIsUnmodifiable();
        list.remove(element);
        return (E)this;
    }

    public <E extends AList<T>> E makeUnmodifiable() {
        isUnmodifiable = true;
        return (E)this;
    }

    public <E extends AList<T>> E filter(Predicate<T> cond) {
        E list = create();

        for (T element : this.list) {
            if (cond.test(element)) {
                list.add(element);
            }
        }

        return list;
    }

    public T findAny(Predicate<T> cond) {
        return list.stream()
            .filter(cond)
            .findAny()
            .orElse(null);
    }

    public T getAny() {
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public Stream<T> stream() {
        return list.stream();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The list is unmodifiable");
        }
    }
}
