package com.tsoft.civilization.world.economic;

import com.tsoft.civilization.world.Civilization;

public class CivilizationScoreMock extends CivilizationScore {
    private int food;
    private int production;
    private int gold;
    private int science;
    private int culture;
    private int happiness;
    private int population;

    public CivilizationScoreMock(Civilization civilization,
                                 int food, int production, int gold, int science, int culture, int happiness, int population) {
        super(civilization);

        this.food = food;
        this.production = production;
        this.gold = gold;
        this.science = science;
        this.culture = culture;
        this.happiness = happiness;
        this.population = population;
    }

    public boolean isEqualTo(CivilizationScore score) {
        boolean isEqual = food == score.getFood() &&
                production == score.getProduction() &&
                gold == score.getGold();

        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.println(String.format(
                "Type    | Expected   Got\n" +
                "------------------------------\n" +
                "Food    |     %1$d      %2$d\n" +
                "Product |     %3$d      %4$d\n" +
                "Gold    |     %5$d      %6$d\n" +
                "Science |     %7$d      %8$d\n" +
                "Culture |     %9$d      %10$d\n" +
                "Happine |    %11$d      %12$d\n" +
                "Populat |    %13$d      %14$d\n",
                food, score.getFood(),
                production, score.getProduction(),
                gold, score.getGold(),
                science, score.getScience(),
                culture, score.getCulture(),
                happiness, score.getHappiness(),
                population, score.getPopulation()
            ));
        }

        return isEqual;
    }
}
