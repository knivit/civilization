package com.tsoft.civilization.economic;

import java.util.Comparator;

public class SupplyMock {

    public static Supply of(String str) {
        StringParser<Supply> parser = new StringParser<>();
        return parser.parse(str, Supply.EMPTY, SupplyMock::getSupply, Supply::add);
    }

    private static Supply getSupply(char type, int value) {
        switch (type) {
            case 'F': return Supply.builder().food(value).build();
            case 'P': return Supply.builder().production(value).build();
            case 'G': return Supply.builder().gold(value).build();
            case 'S': return Supply.builder().science(value).build();
            case 'C': return Supply.builder().culture(value).build();
            case 'A': return Supply.builder().faith(value).build();
            case 'T': return Supply.builder().tourism(value).build();
            case 'E': return Supply.builder().greatPerson(value).build();
        }

        throw new IllegalArgumentException("Unknown type = " + type);
    }

    private static final Comparator<Supply> comparator = (a, b) -> equals(a, b) ? 0 : 1;

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

    public static int compare(Supply a, Supply b) {
        return comparator.compare(a, b);
    }
}
