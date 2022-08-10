package com.tsoft.civilization.civilization.city.construction;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
public class ConstructionList implements Iterable<Construction> {

    // The list already sorted by priority; from first to be build to the last
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

    // Increase the priority
    public void up(Construction construction) {
        int index = constructions.indexOf(construction);
        if (index == -1) {
            log.debug("Construction {} not found in the list", construction);
            return;
        }

        if (index > 0) {
            Construction object = constructions.get(index - 1);
            constructions.set(index - 1, construction);
            constructions.set(index, object);
        }
    }

    // Decrease the priority
    public void down(Construction construction) {
        int index = constructions.indexOf(construction);
        if (index == -1) {
            log.debug("Construction {} not found in the list", construction);
            return;
        }

        if (index < (constructions.size() - 1)) {
            Construction object = constructions.get(index + 1);
            constructions.set(index + 1, construction);
            constructions.set(index, object);
        }
    }

    public void set(int index, Construction construction) {
        Objects.checkIndex(index, constructions.size());
        constructions.add(index, construction);
    }
}
