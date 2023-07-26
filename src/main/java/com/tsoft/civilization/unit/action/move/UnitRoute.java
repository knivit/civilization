package com.tsoft.civilization.unit.action.move;

import com.tsoft.civilization.util.dir.AbstractDir;

import java.util.*;
import java.util.stream.Stream;

/*
 * Unit's route is an array of directions
 */
public class UnitRoute implements Iterable<AbstractDir> {

    private final ArrayList<AbstractDir> dirs = new ArrayList<>();

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

    public boolean isEmpty() {
        return dirs.isEmpty();
    }

    public int size() {
        return dirs.size();
    }
}
