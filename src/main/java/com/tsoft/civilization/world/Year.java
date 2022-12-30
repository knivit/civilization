package com.tsoft.civilization.world;

import com.tsoft.civilization.util.l10n.L10n;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// immutable
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "year")
public class Year {

    public static final Year NOT_STARTED = new Year(-1, 0, false);

    public static final int ANCIENT_ERA = 1000;
    public static final int CLASSICAL_ERA = 1200;
    public static final int MEDIEVAL_ERA = 1600;
    public static final int RENAISSANCE_ERA = 1800;
    public static final int INDUSTRIAL_ERA = 1900;
    public static final int MODERN_ERA = 1950;
    public static final int ATOMIC_ERA = 2050;
    public static final int INFORMATION_ERA = Integer.MAX_VALUE;

    private final int year;
    private final int stepNo;
    private final boolean isNewEra;

    public Year(Integer year) {
        this.year = year;
        this.stepNo = 0;
        this.isNewEra = true;
    }

    public boolean isAfter(int year) {
        return this.year >= year;
    }

    public int getEra() {
        return getEra(year);
    }

    public int getEra(int year) {
        if (year <= ANCIENT_ERA) return ANCIENT_ERA;
        if (year <= CLASSICAL_ERA) return CLASSICAL_ERA;
        if (year <= MEDIEVAL_ERA) return MEDIEVAL_ERA;
        if (year <= RENAISSANCE_ERA) return RENAISSANCE_ERA;
        if (year <= INDUSTRIAL_ERA) return INDUSTRIAL_ERA;
        if (year <= MODERN_ERA) return MODERN_ERA;
        if (year <= ATOMIC_ERA) return ATOMIC_ERA;
        return INFORMATION_ERA;
    }

    public Year nextYear() {
        if (year == -1) {
            throw new IllegalStateException("The game isn't started");
        }

        int nextValue;
        if (year < -2000) nextValue = year + 150; else
        if (year < -1000) nextValue = year + 120; else
        if (year < 0) nextValue = year + 100; else
        if (year < 500) nextValue = year + 80; else
        if (year < 1000) nextValue = year + 50; else
        if (year < 1500) nextValue = year + 20; else
        if (year < 2000) nextValue = year + 10;
        else nextValue = year + 1;

        boolean isNewEra = getEra() != getEra(nextValue);
        return new Year(nextValue, stepNo + 1, isNewEra);
    }

    public String getYearLocalized() {
        if (year == -1) {
            return L10nWorld.NOT_STARTED.getLocalized();
        }

        if (year < 0) {
            return Math.abs(year) + " BC";
        }

        return Math.abs(year) + " AC";
    }

    public L10n getEraLocalized() {
        if (year == -1) return L10nWorld.NOT_STARTED;

        if (year <= ANCIENT_ERA) return L10nWorld.ANCIENT_ERA;
        if (year <= CLASSICAL_ERA) return L10nWorld.CLASSICAL_ERA;
        if (year <= MEDIEVAL_ERA) return L10nWorld.MEDIEVAL_ERA;
        if (year <= RENAISSANCE_ERA) return L10nWorld.RENAISSANCE_ERA;
        if (year <= INDUSTRIAL_ERA) return L10nWorld.INDUSTRIAL_ERA;
        if (year <= MODERN_ERA) return L10nWorld.MODERN_ERA;
        if (year <= ATOMIC_ERA) return L10nWorld.ATOMIC_ERA;

        return L10nWorld.INFORMATION_ERA;
    }

    @Override
    public String toString() {
        return "Year{" + year + '}';
    }
}
