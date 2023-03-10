package com.tsoft.civilization.civilization.happiness;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoldenAge {

    private int turnsTillGoldenAgeEnds;
    private int goldenAgeCounter;
    private int nextGoldenAgeCounter;

}
