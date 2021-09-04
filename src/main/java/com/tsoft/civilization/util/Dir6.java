package com.tsoft.civilization.util;

import java.util.ArrayList;

/*
 * Move Directions for 4x4 Map
 *
 * 1) For odd rows:
 *  +---+---+---+--       +---+---+---+
 *  |   | * | * |         |   | * | * |
 *  --+---+---+---+       +---+---+---+
 *    | * | X | * |  -->  | * | X | * |
 *  --+---+---+---+       +---+---+---+
 *  |   | * | * |         |   | * | * |
 *  +---+---+---+--       +---+---+---+
 *
 * 2) For even rows:
 *  --+---+---+---+       +---+---+---+
 *    | * | * |   |  -->  | * | * |   |
 *  --+---+---+---+       +---+---+---+
 *  | * | X | * |         | * | X | * |
 *  +---+---+---+--       +---+---+---+
 *    | * | * |   |       | * | * |   |
 *  +---+---+---+--       +---+---+---+
 *
 */
public class Dir6 extends AbstractDir {

    private static final ArrayList<Dir6> evenDirs = new ArrayList<>();
    private static final ArrayList<Dir6> inverseEvenDirs = new ArrayList<>();
    private static final ArrayList<Dir6> oddDirs = new ArrayList<>();
    private static final ArrayList<Dir6> inverseOddDirs = new ArrayList<>();

    private static final Dir6 sDir = new Dir6(-1, -1);

    static {
        evenDirs.add(sDir);
        evenDirs.add(new Dir6(0, -1));
        evenDirs.add(new Dir6(1, 0));
        evenDirs.add(new Dir6(0, 1));
        evenDirs.add(new Dir6(-1, 1));
        evenDirs.add(new Dir6(-1, 0));

        inverseEvenDirs.add(new Dir6(1, 1));
        inverseEvenDirs.add(new Dir6(0, 1));
        inverseEvenDirs.add(new Dir6(-1, 0));
        inverseEvenDirs.add(new Dir6(0, -1));
        inverseEvenDirs.add(new Dir6(1, -1));
        inverseEvenDirs.add(new Dir6(1, 0));

        oddDirs.add(new Dir6(0, -1));
        oddDirs.add(new Dir6(1, -1));
        oddDirs.add(new Dir6(1, 0));
        oddDirs.add(new Dir6(1, 1));
        oddDirs.add(new Dir6(0, 1));
        oddDirs.add(new Dir6(-1, 0));

        inverseOddDirs.add(new Dir6(0, 1));
        inverseOddDirs.add(new Dir6(-1, 1));
        inverseOddDirs.add(new Dir6(-1, 0));
        inverseOddDirs.add(new Dir6(-1, -1));
        inverseOddDirs.add(new Dir6(0, -1));
        inverseOddDirs.add(new Dir6(1, 0));
    }

    public Dir6(int dX, int dY) {
        super(dX, dY);

        boolean isLeft = (dX == -1 && (dY == 0 || dY == -1 || dY == 1));
        boolean isCurrent = (dX == 0 && (dY == -1 || dY == 1));
        boolean isRight = (dX == 1 && (dY == 0 || dY == -1 || dY == 1));

        assert (isLeft || isCurrent || isRight) : "Invalid direction, dx = " + dX + ", dy = " + dY;
    }

    @Override
    public ArrayList<Dir6> getDirs(int y) {
        if ((y % 2) == 0) {
            return evenDirs;
        }
        return oddDirs;
    }

    @Override
    public AbstractDir getInverse(int y) {
        ArrayList<Dir6> dirs = oddDirs;
        ArrayList<Dir6> inverseDirs = inverseOddDirs;
        if ((y % 2) == 0) {
            dirs = evenDirs;
            inverseDirs = inverseEvenDirs;
        }

        int i = dirs.indexOf(this);
        return inverseDirs.get(i);
    }

    public static ArrayList<Dir6> staticGetDirs(int y) {
        return sDir.getDirs(y);
    }
}

