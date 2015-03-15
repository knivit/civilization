package com.tsoft.civilization.world;

public class TwoCivilizationsKey {
    private Civilization c1;
    private Civilization c2;

    public TwoCivilizationsKey(Civilization c1, Civilization c2) {
        if (c1 == null || c2 == null || c1.equals(c2)) {
            throw new IllegalArgumentException("Civilizations must be different and not null");
        }

        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwoCivilizationsKey that = (TwoCivilizationsKey) o;

        if ((c1.equals(that.c1) && c2.equals(that.c2)) ||
                (c1.equals(that.c2) && c2.equals(that.c1))) return true;

        return false;
    }

    @Override
    public int hashCode() {
        int result = c1 != null ? c1.hashCode() : 0;
        result = result + (c2 != null ? c2.hashCode() : 0);
        return result;
    }
}
