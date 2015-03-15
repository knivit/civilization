package com.tsoft.civilization.unit.util;

import com.tsoft.civilization.util.AbstractDir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Unit's route is an array of directions
 */
public class UnitRoute {
    private ArrayList<AbstractDir> dirs = new ArrayList<AbstractDir>();

    // size of the route before unit's movement
    private int originalSize;

    public UnitRoute(AbstractDir ... dirs) {
        if (dirs != null) {
            this.dirs.addAll(Arrays.asList(dirs));
        }
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
