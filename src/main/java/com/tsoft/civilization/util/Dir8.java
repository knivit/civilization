package com.tsoft.civilization.util;

import java.util.ArrayList;

/*
 * Move Directions for 4x4 Map
 * Possible values:
 *  +---+---+---+
 *  | * | * | * |
 *  +---+---+---+
 *  | * | X | * |
 *  +---+---+---+
 *  | * | * | * |
 *  +---+---+---+
 */
public class Dir8 extends AbstractDir {
    private static ArrayList<Dir8> dirs = new ArrayList<Dir8>();

    static {
        dirs.add(new Dir8(-1, -1));
        dirs.add(new Dir8(-1, 0));
        dirs.add(new Dir8(-1, 1));
        dirs.add(new Dir8(0, -1));
        dirs.add(new Dir8(0, 1));
        dirs.add(new Dir8(1, -1));
        dirs.add(new Dir8(1, 0));
        dirs.add(new Dir8(1, 1));
    }

    public Dir8(int dX, int dY) {
        super(dX, dY);

        boolean isLeft = (dX == -1 && (dY == -1 || dY == 0 || dY == 1));
        boolean isCurrent = (dX == 0 && (dY == -1 || dY == 1));
        boolean isRight = (dX == 1 && (dY == -1 || dY == 0 || dY == 1));

        assert (isLeft || isCurrent || isRight) : "Invalid direction, dx = " + dX + ", dy = " + dY;
    }

    @Override
    public ArrayList<Dir8> getDirs(int y) {
        return dirs;
    }

    @Override
    public AbstractDir getInverse(int y) {
        return dirs.get(0);
    }
}
