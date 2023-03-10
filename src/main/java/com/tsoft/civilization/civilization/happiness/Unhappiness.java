package com.tsoft.civilization.civilization.happiness;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Unhappiness {

    public static final Unhappiness EMPTY = Unhappiness.builder().build();

    private final int inCities;
    private final int population;

    // i.e.
    private final int inMyCities;
    private final int inAnnexedCities;
    private final int inPuppetCities;

    public Unhappiness add(Unhappiness other) {
        return Unhappiness.builder()
            .inCities(inCities + other.inCities)
            .inMyCities(inMyCities + other.inMyCities)
            .inAnnexedCities(inAnnexedCities + other.inAnnexedCities)
            .inPuppetCities(inPuppetCities + other.inPuppetCities)
            .population(population + other.population)
            .build();
    }

    public int getTotal() {
        return inCities + population;
    }

    @Override
    public String toString() {
        return "{" +
            "inMyCities=" + inMyCities +
            ", inAnnexedCities=" + inAnnexedCities +
            ", inPuppetCities=" + inPuppetCities +
            ", population=" + population +
            ", total=" + getTotal() +
            '}';
    }
}
