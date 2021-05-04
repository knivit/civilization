package com.tsoft.civilization.economic;

import com.tsoft.civilization.civilization.population.Unhappiness;

import java.util.Comparator;

public class UnhappinessMock {

    public static Unhappiness of(String str) {
        StringParser<Unhappiness> parser = new StringParser<>();
        return parser.parse(str, Unhappiness.EMPTY, UnhappinessMock::getUnhappiness, Unhappiness::add);
    }

    private static Unhappiness getUnhappiness(Character type, Integer value) {
        switch (type) {
            case 'C': return Unhappiness.builder().inMyCities(value).build();
            case 'A': return Unhappiness.builder().inAnnexedCities(value).build();
            case 'U': return Unhappiness.builder().inPuppetCities(value).build();
            case 'P': return Unhappiness.builder().population(value).build();
            case 'T': return Unhappiness.builder().total(value).build();
        }

        throw new IllegalArgumentException("Unknown type = " + type);
    }

    private static final Comparator<Unhappiness> comparator = (a, b) -> equals(a, b) ? 0 : 1;

    public static boolean equals(Unhappiness a, Unhappiness b) {
        boolean isEqual =
            a.getInMyCities() == b.getInMyCities() &&
            a.getInAnnexedCities() == b.getInAnnexedCities() &&
            a.getInPuppetCities() == b.getInPuppetCities() &&
            a.getPopulation() == b.getPopulation() &&
            a.getTotal() == b.getTotal();

        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.printf("""
                Type            |   Actual | Expected
                -----------------------------------------
                MyCities        |     %5d  |    %5d | %s
                AnnexedCities   |     %5d  |    %5d | %s
                PuppetCities    |     %5d  |    %5d | %s
                Population      |     %5d  |    %5d | %s
                Total           |     %5d  |    %5d | %s
                %n""",

                a.getInMyCities(), b.getInMyCities(), cmp(a.getInMyCities(), b.getInMyCities()),
                a.getInAnnexedCities(), b.getInAnnexedCities(), cmp(a.getInAnnexedCities(), b.getInAnnexedCities()),
                a.getInPuppetCities(), b.getInPuppetCities(), cmp(a.getInPuppetCities(), b.getInPuppetCities()),
                a.getPopulation(), b.getPopulation(), cmp(a.getPopulation(), b.getPopulation()),
                a.getTotal(), b.getTotal(), cmp(a.getTotal(), b.getTotal())
            );
        }

        return isEqual;
    }

    private static String cmp(int a, int b) {
        return (a == b) ? "" : "*";
    }

    public static int compare(Unhappiness a, Unhappiness b) {
        return comparator.compare(a, b);
    }
}

