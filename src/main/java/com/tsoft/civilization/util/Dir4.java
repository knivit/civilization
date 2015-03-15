package com.tsoft.civilization.util;

import java.util.ArrayList;

/*
 * Move Directions for 4x4 Map
 * Possible values:
 *  +---+---+---+
 *  |   | * |   |
 *  +---+---+---+
 *  | * | X | * |
 *  +---+---+---+
 *  |   | * |   |
 *  +---+---+---+
 */
public class Dir4 extends AbstractDir {
    private static ArrayList<Dir4> dirs = new ArrayList<Dir4>();

    static {
        dirs.add(new Dir4(-1, 0));
        dirs.add(new Dir4(0, -1));
        dirs.add(new Dir4(0, 1));
        dirs.add(new Dir4(1, 0));
    }

    public Dir4(int dX, int dY) {
        super(dX, dY);

        boolean isLeft = (dX == -1 && dY == 0);
        boolean isCurrent = (dX == 0 && (dY == -1 || dY == 1));
        boolean isRight = (dX == 1 && dY == 0);

        assert (isLeft || isCurrent || isRight) : "Invalid direction, dx = " + dX + ", dy = " + dY;
    }

    @Override
    public ArrayList<Dir4> getDirs(int y) {
        return dirs;
    }

    @Override
    public AbstractDir getInverse(int y) {
        return dirs.get(0);
    }
}
