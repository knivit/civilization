package com.tsoft.civilization.unit.service.move;

import com.tsoft.civilization.util.AbstractDir;

import java.util.*;
import java.util.stream.Stream;

/*
 * Unit's route is an array of directions
 */
public class UnitRoute implements Iterable<AbstractDir> {
    private final ArrayList<AbstractDir> dirs = new ArrayList<>();

    // size of the route before unit's movement
    private int originalSize;

    public UnitRoute(AbstractDir ... dirs) {
        if (dirs != null) {
            this.dirs.addAll(Arrays.asList(dirs));
        }
    }

    @Override
    public Iterator<AbstractDir> iterator() {
        return dirs.listIterator();
    }

    public Stream<AbstractDir> stream() {
        return dirs.stream();
    }

    public void add(AbstractDir dir) {
        dirs.add(dir);
    }

    public void addAll(List<AbstractDir> dirs) {
        this.dirs.addAll(dirs);
    }

    public ArrayList<AbstractDir> getDirs() {
        return dirs;
    }

    public int getOriginalSize() {
        return originalSize;
    }

    public void saveOriginalSize() {
        originalSize = getDirs().size();
    }

    public boolean isEmpty() {
        return dirs.isEmpty();
    }

    public int size() {
        return dirs.size();
    }
}
