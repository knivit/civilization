package com.tsoft.civilization.civilization.happiness;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Happiness {

    public static final Happiness EMPTY = Happiness.builder().build();

    private final int luxuryResources;
    private final int buildings;
    private final int wonders;
    private final int naturalWonders;
    private final int socialPolicies;
    private final int garrisonedUnits;
    private final int difficultyLevel;

    public int getTotal() {
        return luxuryResources + buildings + wonders + naturalWonders + socialPolicies + garrisonedUnits + difficultyLevel;
    }
}
