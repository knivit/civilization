package com.tsoft.civilization.economic;

import com.tsoft.civilization.civilization.population.Happiness;

import java.util.Comparator;

public class HappinessMock {

    public static Happiness of(String str) {
        StringParser<Happiness> parser = new StringParser<>();
        return parser.parse(str, Happiness.EMPTY, HappinessMock::getHappiness, Happiness::add);
    }

    private static Happiness getHappiness(Character type, Integer value) {
        switch (type) {
            case 'D': return Happiness.builder().baseHappiness(value).build();
            case 'L': return Happiness.builder().luxuryResources(value).build();
            case 'B': return Happiness.builder().buildings(value).build();
            case 'W': return Happiness.builder().wonders(value).build();
            case 'N': return Happiness.builder().naturalWonders(value).build();
            case 'P': return Happiness.builder().socialPolicies(value).build();
            case 'G': return Happiness.builder().garrisonedUnits(value).build();
        }

        throw new IllegalArgumentException("Unknown type = " + type);
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
