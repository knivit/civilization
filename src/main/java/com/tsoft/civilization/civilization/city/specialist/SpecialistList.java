package com.tsoft.civilization.civilization.city.specialist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class SpecialistList implements Iterable<Specialist> {

    private final List<Specialist> specialists = new ArrayList<>();
    private boolean isUnmodifiable;

    public SpecialistList() { }

    public SpecialistList unmodifiableList() {
        SpecialistList list = new SpecialistList();
        list.specialists.addAll(specialists);
        list.isUnmodifiable = true;
        return list;
    }

    @Override
    public Iterator<Specialist> iterator() {
        return specialists.listIterator();
    }

    public Stream<Specialist> stream() {
        return specialists.stream();
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The list is unmodifiable");
        }
    }

    public SpecialistList add(Specialist specialist) {
        checkIsUnmodifiable();
        specialists.add(specialist);
        return this;
    }

    public Specialist get(int index) {
        return specialists.get(index);
    }

    public SpecialistList remove(Specialist specialist) {
        specialists.remove(specialist);
        return this;
    }

    public boolean isEmpty() {
        return specialists.isEmpty();
    }

    public int size() {
        return specialists.size();
    }
}
