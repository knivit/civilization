package com.tsoft.civilization.civilization.happiness;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Unhappiness {

    private final int inMyCities;
    private final int inAnnexedCities;
    private final int inPuppetCities;
    private final int population;
    private final int total;
}
