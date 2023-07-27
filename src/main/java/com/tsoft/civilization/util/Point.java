package com.tsoft.civilization.util;

import static com.tsoft.civilization.util.Format.format;

// Immutable
public class Point {

    private final int x;
    private final int y;

    // Don't implement "add" method
    // As the used map is cyclic, use TilesMap.addDirToLocation() instead
    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public Point(int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException(format("x = {} must be >= 0", x));
        }
        if (y < 0) {
            throw new IllegalArgumentException(format("y = {} must be >= 0", y));
        }

        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        if (y != point.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 10001 * y + x;
    }

    @Override
    public String toString() {
        return  "{x=" + x +
            ", y=" + y +
            '}';
    }
}
