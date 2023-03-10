package com.tsoft.civilization.civilization.happiness;

import com.tsoft.civilization.civilization.Civilization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CivilizationGoldenAgeService {

    private final Civilization civilization;

    private GoldenAge goldenAge = GoldenAge.builder()
        .goldenAgeCounter(0)
        .nextGoldenAgeCounter(16)
        .turnsTillGoldenAgeEnds(0)
        .build();

    public GoldenAge getGoldenAge() {
        return goldenAge;
    }

    public void startYear() {
        int goldenAgeCounter = goldenAge.getGoldenAgeCounter() + calcHappiness();

        // TODO by difficulty level
        if (goldenAgeCounter > goldenAge.getNextGoldenAgeCounter()) {
            int turnsTillGoldenAgeEnds = 10;
            int nextGoldenAgeCounter = goldenAge.getNextGoldenAgeCounter() * 2;

            goldenAge = GoldenAge.builder()
                .goldenAgeCounter(0)
                .nextGoldenAgeCounter(nextGoldenAgeCounter)
                .turnsTillGoldenAgeEnds(turnsTillGoldenAgeEnds)
                .build();
        }
    }

    public void stopYear() {
        int turnsTillGoldenAgeEnds = goldenAge.getTurnsTillGoldenAgeEnds();

        if (turnsTillGoldenAgeEnds > 0) {
            turnsTillGoldenAgeEnds --;

            goldenAge = GoldenAge.builder()
                .goldenAgeCounter(goldenAge.getGoldenAgeCounter())
                .nextGoldenAgeCounter(goldenAge.getNextGoldenAgeCounter())
                .turnsTillGoldenAgeEnds(turnsTillGoldenAgeEnds)
                .build();
        }
    }

    private int calcHappiness() {
        Happiness happiness = civilization.getHappiness();
        Unhappiness unhappiness = civilization.getUnhappiness();
        return Math.max(0, happiness.getTotal() - unhappiness.getTotal());
    }
}
