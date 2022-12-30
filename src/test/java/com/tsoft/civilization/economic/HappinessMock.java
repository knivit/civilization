package com.tsoft.civilization.economic;

import com.tsoft.civilization.StringParser;
import com.tsoft.civilization.civilization.happiness.Happiness;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class HappinessMock {

    private static final String BASE_HAPPINESS = "D";
    private static final String LUXURY_RESOURCES = "L";
    private static final String BUILDINGS = "B";
    private static final String WONDERS = "W";
    private static final String NATURAL_WONDERS = "N";
    private static final String SOCIAL_POLICIES = "P";
    private static final String GARRISONED_UNITS = "G";

    private static final Set<String> AVAILABLE_IDENTIFIERS = Set.of(
        BASE_HAPPINESS, LUXURY_RESOURCES, BUILDINGS, WONDERS, NATURAL_WONDERS, SOCIAL_POLICIES, GARRISONED_UNITS
    );

    public static Happiness of(String str) {
        StringParser parser = new StringParser();
        return build(parser.parse(str, AVAILABLE_IDENTIFIERS));
    }

    private static Happiness build(Map<String, Integer> map) {
        int baseHappiness = map.getOrDefault(BASE_HAPPINESS, 0);
        int luxuryResources = map.getOrDefault(LUXURY_RESOURCES, 0);
        int buildings = map.getOrDefault(BUILDINGS, 0);
        int wonders = map.getOrDefault(WONDERS, 0);
        int naturalWonders = map.getOrDefault(NATURAL_WONDERS, 0);
        int socialPolicies = map.getOrDefault(SOCIAL_POLICIES, 0);
        int garrisonedUnits = map.getOrDefault(GARRISONED_UNITS, 0);

        return Happiness.builder()
            .baseHappiness(baseHappiness)
            .luxuryResources(luxuryResources)
            .buildings(buildings)
            .wonders(wonders)
            .naturalWonders(naturalWonders)
            .socialPolicies(socialPolicies)
            .garrisonedUnits(garrisonedUnits)
            .build();
    }

    private static final Comparator<Happiness> comparator = (a, b) -> equals(a, b) ? 0 : 1;

    public static boolean equals(Happiness a, Happiness b) {
        boolean isEqual =
            a.getLuxuryResources() == b.getLuxuryResources() &&
            a.getBuildings() == b.getBuildings() &&
            a.getWonders() == b.getWonders() &&
            a.getNaturalWonders() == b.getNaturalWonders() &&
            a.getSocialPolicies() == b.getSocialPolicies() &&
            a.getGarrisonedUnits() == b.getGarrisonedUnits() &&
            a.getBaseHappiness() == b.getBaseHappiness();

        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.printf("""
                Type            |   Actual | Expected
                -----------------------------------------
                baseHappiness   |     %5d  |    %5d | %s
                luxuryResources |     %5d  |    %5d | %s
                buildings       |     %5d  |    %5d | %s
                wonders         |     %5d  |    %5d | %s
                naturalWonders  |     %5d  |    %5d | %s
                socialPolicies  |     %5d  |    %5d | %s
                garrisonedUnits |     %5d  |    %5d | %s
                %n""",

                a.getBaseHappiness(), b.getBaseHappiness(), cmp(a.getBaseHappiness(), b.getBaseHappiness()),
                a.getLuxuryResources(), b.getLuxuryResources(), cmp(a.getLuxuryResources(), b.getLuxuryResources()),
                a.getBuildings(), b.getBuildings(), cmp(a.getBuildings(), b.getBuildings()),
                a.getWonders(), b.getWonders(), cmp(a.getWonders(), b.getWonders()),
                a.getNaturalWonders(), b.getNaturalWonders(), cmp(a.getNaturalWonders(), b.getNaturalWonders()),
                a.getSocialPolicies(), b.getSocialPolicies(), cmp(a.getSocialPolicies(), b.getSocialPolicies()),
                a.getGarrisonedUnits(), b.getGarrisonedUnits(), cmp(a.getGarrisonedUnits(), b.getGarrisonedUnits())
            );
        }

        return isEqual;
    }

    private static String cmp(int a, int b) {
        return (a == b) ? "" : "*";
    }

    public static int compare(Happiness a, Happiness b) {
        return comparator.compare(a, b);
    }
}
