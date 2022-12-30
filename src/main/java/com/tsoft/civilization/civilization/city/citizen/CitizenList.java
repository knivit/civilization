package com.tsoft.civilization.civilization.city.citizen;

import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CitizenList implements Iterable<Citizen> {

    private final List<Citizen> citizens = new ArrayList<>();
    private boolean isUnmodifiable;

    public CitizenList() { }

    public CitizenList unmodifiableList() {
        CitizenList list = new CitizenList();
        list.citizens.addAll(citizens);
        list.isUnmodifiable = true;
        return list;
    }

    @Override
    public Iterator<Citizen> iterator() {
        return citizens.listIterator();
    }

    public Stream<Citizen> stream() {
        return citizens.stream();
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The list is unmodifiable");
        }
    }

    public CitizenList add(Citizen citizen) {
        checkIsUnmodifiable();
        citizens.add(citizen);
        return this;
    }

    public Citizen get(int index) {
        return citizens.get(index);
    }

    public CitizenList remove(Citizen citizen) {
        citizens.remove(citizen);
        return this;
    }

    public boolean isEmpty() {
        return citizens.isEmpty();
    }

    public int size() {
        return citizens.size();
    }

    public List<Point> getLocations() {
        return citizens.stream()
            .map(Citizen::getLocation)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}
