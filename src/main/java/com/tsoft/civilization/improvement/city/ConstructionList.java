package com.tsoft.civilization.improvement.city;

import java.util.*;
import java.util.stream.Stream;

public class ConstructionList implements Iterable<Construction> {
    private final List<Construction> constructions = new ArrayList<>();
    private boolean isUnmodifiable;

    public ConstructionList() { }

    public ConstructionList(List<Construction> constructions) {
        Objects.requireNonNull(constructions);
        this.constructions.addAll(constructions);
    }

    public ConstructionList(Construction ... constructions) {
        if (constructions != null) {
            this.constructions.addAll(Arrays.asList(constructions));
        }
        isUnmodifiable = true;
    }

    public List<Construction> getList() {
        return new ArrayList<>(constructions);
    }

    public ConstructionList unmodifiableList() {
        ConstructionList list = new ConstructionList();
        list.constructions.addAll(constructions);
        list.isUnmodifiable = true;
        return list;
    }

    @Override
    public Iterator<Construction> iterator() {
        return constructions.iterator();
    }

    public Stream<Construction> stream() {
        return constructions.stream();
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The list is unmodifiable");
        }
    }

    public boolean isEmpty() {
        return constructions.isEmpty();
    }

    public int size() {
        return constructions.size();
    }

    public ConstructionList add(Construction construction) {
        Objects.requireNonNull(construction, "construction can't be null");

        checkIsUnmodifiable();
        constructions.add(construction);
        return this;
    }

    public ConstructionList remove(Construction construction) {
        Objects.requireNonNull(construction, "construction can't be null");

        checkIsUnmodifiable();
        constructions.remove(construction);
        return this;
    }
}
