package com.tsoft.civilization.util;

import java.util.ArrayList;

public abstract class AbstractDir {
    private final int dX;
    private final int dY;

    public abstract ArrayList<? extends AbstractDir> getDirs(int y);

    public abstract AbstractDir getInverse(int y);

    public AbstractDir(int dX, int dY) {
        assert (dX == -1 || dX == 0 || dX == 1) : "Invalid direction, dx = " + dX + ", must be [-1, 0, 1]";
        assert (dY == -1 || dY == 0 || dY == 1) : "Invalid direction, dy = " + dY + ", must be [-1, 0, 1]";

        this.dX = dX;
        this.dY = dY;
    }

    public int getDX() {
        return dX;
    }

    public int getDY() {
        return dY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDir abstractDir = (AbstractDir) o;

        if (dX != abstractDir.dX) return false;
        if (dY != abstractDir.dY) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dX;
        result = 31 * result + dY;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Dir");
        sb.append("{dX=").append(dX);
        sb.append(", dY=").append(dY);
        sb.append('}');
        return sb.toString();
    }
}
