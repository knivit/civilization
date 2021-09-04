package com.tsoft.civilization.economic;

import com.tsoft.civilization.StringParser;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class SupplyMock {

    private static final String FOOD = "F";
    private static final String PRODUCTION = "P";
    private static final String GOLD = "G";
    private static final String SCIENCE = "S";
    private static final String CULTURE = "C";
    private static final String FAITH = "A";
    private static final String TOURISM = "T";
    private static final String GREAT_PERSON = "E";

    private static final Set<String> AVAILABLE_IDENTIFIERS = Set.of(
        FOOD, PRODUCTION, GOLD, SCIENCE, CULTURE, FAITH, TOURISM, GREAT_PERSON
    );

    public static Supply of(String str) {
        StringParser parser = new StringParser();
        return build(parser.parse(str, AVAILABLE_IDENTIFIERS));
    }

    private static Supply build(Map<String, Integer> map) {
        int food = map.getOrDefault(FOOD, 0);
        int production = map.getOrDefault(PRODUCTION, 0);
        int gold = map.getOrDefault(GOLD, 0);
        int science = map.getOrDefault(SCIENCE, 0);
        int culture = map.getOrDefault(CULTURE, 0);
        int faith = map.getOrDefault(FAITH, 0);
        int tourism = map.getOrDefault(TOURISM, 0);
        int greatPerson = map.getOrDefault(GREAT_PERSON, 0);

        return Supply.builder()
            .food(food)
            .production(production)
            .gold(gold)
            .science(science)
            .culture(culture)
            .faith(faith)
            .tourism(tourism)
            .greatPerson(greatPerson)
            .build();
    }

    private static final Comparator<Supply> comparator = (a, b) -> equals(a, b) ? 0 : 1;

    public static int compare(Supply a, Supply b) {
        return comparator.compare(a, b);
    }

    public static boolean equals(Supply a, Supply b) {
        boolean isEqual =
            a.getFood() == b.getFood() &&
            a.getProduction() == b.getProduction() &&
            a.getGold() == b.getGold() &&
            a.getScience() == b.getScience() &&
            a.getCulture() == b.getCulture() &&
            a.getFaith() == b.getFaith() &&
            a.getTourism() == b.getTourism() &&
            a.getGreatPerson() == b.getGreatPerson();

        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.printf("""
                Type            |   Actual   | Expected
                -----------------------------------------
                Food            |     %5d  |    %5d | %s
                Production      |     %5d  |    %5d | %s
                Gold            |     %5d  |    %5d | %s
                Science         |     %5d  |    %5d | %s
                Culture         |     %5d  |    %5d | %s
                Faith           |     %5d  |    %5d | %s
                Tourism         |     %5d  |    %5d | %s
                Great Person    |     %5d  |    %5d | %s
                %n""",

                a.getFood(), b.getFood(), cmp(a.getFood(), b.getFood()),
                a.getProduction(), b.getProduction(), cmp(a.getProduction(), b.getProduction()),
                a.getGold(), b.getGold(), cmp(a.getGold(), b.getGold()),
                a.getScience(), b.getScience(), cmp(a.getScience(), b.getScience()),
                a.getCulture(), b.getCulture(), cmp(a.getCulture(), b.getCulture()),

                a.getFaith(), b.getFaith(), cmp(a.getFaith(), b.getFaith()),
                a.getTourism(), b.getTourism(), cmp(a.getTourism(), b.getTourism()),
                a.getGreatPerson(), b.getGreatPerson(), cmp(a.getGreatPerson(), b.getGreatPerson())
            );
        }

        return isEqual;
    }

    private static String cmp(int a, int b) {
        return (a == b) ? "" : "*";
    }
}
