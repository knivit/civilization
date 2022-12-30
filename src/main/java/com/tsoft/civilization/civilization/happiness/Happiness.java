package com.tsoft.civilization.civilization.happiness;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Happiness {

    public static final Happiness EMPTY = Happiness.builder().build();

    private final int baseHappiness;
    private final int luxuryResources;
    private final int buildings;
    private final int wonders;
    private final int naturalWonders;
    private final int socialPolicies;
    private final int garrisonedUnits;

    public int getTotal() {
        return baseHappiness + luxuryResources + buildings + wonders + naturalWonders + socialPolicies + garrisonedUnits;
    }

    public Happiness add(Happiness other) {
        return Happiness.builder()
            .baseHappiness(baseHappiness + other.baseHappiness)
            .luxuryResources(luxuryResources + other.luxuryResources)
            .buildings(buildings + other.buildings)
            .wonders(wonders + other.wonders)
            .naturalWonders(naturalWonders + other.naturalWonders)
            .socialPolicies(socialPolicies + other.socialPolicies)
            .garrisonedUnits(garrisonedUnits + other.garrisonedUnits)
            .build();
    }

    @Override
    public String toString() {
        return "{" +
            "baseHappiness=" + baseHappiness +
            ", luxuryResources=" + luxuryResources +
            ", buildings=" + buildings +
            ", wonders=" + wonders +
            ", naturalWonders=" + naturalWonders +
            ", socialPolicies=" + socialPolicies +
            ", garrisonedUnits=" + garrisonedUnits +
            '}';
    }
}
