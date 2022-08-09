package com.tsoft.civilization.util;

// Immutable
public class Rect {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    public Rect(int x1, int y1, int x2, int y2) {
        assert (x1 != x2) : "x1 = " + x1 + " must be not equal x2 = " + x2;
        assert (y1 != y2) : "y1 = " + y1 + " must be not equal y2 = " + y2;

        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);

        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getWidth() {
        return x2 - x1 + 1;
    }

    public int getHeight() {
        return y2 - y1 + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rect rect = (Rect) o;

        if (x1 != rect.x1) return false;
        if (x2 != rect.x2) return false;
        if (y1 != rect.y1) return false;
        if (y2 != rect.y2) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x1;
        result = 31 * result + y1;
        result = 31 * result + x2;
        result = 31 * result + y2;
        return result;
    }

    @Override
    public String toString() {
        return "[x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + "]";
    }
}
