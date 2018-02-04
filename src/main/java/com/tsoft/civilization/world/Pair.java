package com.tsoft.civilization.world;

public class Pair<T> {
    private T obj1;
    private T obj2;

    public Pair(T obj1, T c2) {
        if (obj1 == null || c2 == null || obj2.equals(c2)) {
            throw new IllegalArgumentException("The objects must be different and not null");
        }

        this.obj1 = obj1;
        this.obj2 = c2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair that = (Pair) o;

        if ((obj1.equals(that.obj1) && obj2.equals(that.obj2)) ||
                (obj1.equals(that.obj2) && obj2.equals(that.obj1))) return true;

        return false;
    }

    @Override
    public int hashCode() {
        int result = obj1 != null ? obj1.hashCode() : 0;
        result = result + (obj2 != null ? obj2.hashCode() : 0);
        return result;
    }
}
