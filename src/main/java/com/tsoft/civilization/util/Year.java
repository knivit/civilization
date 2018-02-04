package com.tsoft.civilization.util;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.L10n.L10nWorld;

import java.util.Objects;

public class Year {
    public static int ANCIENT_ERA = 1000;
    public static int CLASSICAL_ERA = 1200;
    public static int MEDIEVAL_ERA = 1600;
    public static int RENAISSANCE_ERA = 1800;
    public static int INDUSTRIAL_ERA = 1900;
    public static int MODERN_ERA = 1950;
    public static int ATOMIC_ERA = 2050;
    public static int INFORMATION_ERA = Integer.MAX_VALUE;

    private int value;
    private int stepNo;

    public Year(int value) {
        this.value = value;
        stepNo = 0;
    }

    public int getValue() {
        return value;
    }

    public int getStepNo() {
        return stepNo;
    }

    public boolean isBefore(int year) {
        return value < year;
    }

    public boolean isAfter(int year) {
        return value > year;
    }

    public int getEra() {
        if (value <= ANCIENT_ERA) return ANCIENT_ERA;
        if (value <= CLASSICAL_ERA) return CLASSICAL_ERA;
        if (value <= MEDIEVAL_ERA) return MEDIEVAL_ERA;
        if (value <= RENAISSANCE_ERA) return RENAISSANCE_ERA;
        if (value <= INDUSTRIAL_ERA) return INDUSTRIAL_ERA;
        if (value <= MODERN_ERA) return MODERN_ERA;
        if (value <= ATOMIC_ERA) return ATOMIC_ERA;
        return INFORMATION_ERA;
    }

    public Year step() {
        int nextValue;
        if (value < -2000) nextValue = value + 150; else
        if (value < -1000) nextValue = value + 120; else
        if (value < 0) nextValue = value + 100; else
        if (value < 500) nextValue = value + 80; else
        if (value < 1000) nextValue = value + 50; else
        if (value < 1500) nextValue = value + 20; else
        if (value < 2000) nextValue = value + 10;
        else nextValue = value + 1;

        Year nextYear = new Year(nextValue);
        nextYear.stepNo = stepNo + 1;
        return nextYear;
    }

    public String getYearLocalized() {
        if (value < 0) {
            return Math.abs(value) + " B.C";
        }
        return Math.abs(value) + " A.C";
    }

    public L10nMap getEraLocalized() {
        if (value <= ANCIENT_ERA) return L10nWorld.ANCIENT_ERA;
        if (value <= CLASSICAL_ERA) return L10nWorld.CLASSICAL_ERA;
        if (value <= MEDIEVAL_ERA) return L10nWorld.MEDIEVAL_ERA;
        if (value <= RENAISSANCE_ERA) return L10nWorld.RENAISSANCE_ERA;
        if (value <= INDUSTRIAL_ERA) return L10nWorld.INDUSTRIAL_ERA;
        if (value <= MODERN_ERA) return L10nWorld.MODERN_ERA;
        if (value <= ATOMIC_ERA) return L10nWorld.ATOMIC_ERA;

        return L10nWorld.INFORMATION_ERA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Year year = (Year) o;
        return value == year.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
