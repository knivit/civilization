package com.tsoft.civilization.civilization.population;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Unhappiness {

    public static final Unhappiness EMPTY = Unhappiness.builder().build();

    private final int inMyCities;
    private final int inAnnexedCities;
    private final int inPuppetCities;
    private final int population;
    private final int total;

    public Unhappiness add(Unhappiness other) {
        return Unhappiness.builder()
            .inMyCities(inMyCities + other.inMyCities)
            .inAnnexedCities(inAnnexedCities + other.inAnnexedCities)
            .inPuppetCities(inPuppetCities + other.inPuppetCities)
            .population(population + other.population)
            .total(total + other.total)
            .build();
    }

    @Override
    public String toString() {
        return "{" +
            "inMyCities=" + inMyCities +
            ", inAnnexedCities=" + inAnnexedCities +
            ", inPuppetCities=" + inPuppetCities +
            ", population=" + population +
            ", total=" + total +
            '}';
    }
}
