package com.tsoft.civilization.economic;

import com.tsoft.civilization.StringParser;
import com.tsoft.civilization.civilization.happiness.Unhappiness;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class UnhappinessMock {

    private static final String FROM_CITIES = "C";
    private static final String FROM_MY_CITIES = "M";
    private static final String FROM_ANNEXED_CITIES = "A";
    private static final String FROM_PUPPET_CITIES = "U";
    private static final String FROM_POPULATION = "P";
    private static final String TOTAL = "T";

    private static final Set<String> AVAILABLE_IDENTIFIERS = Set.of(
        FROM_CITIES, FROM_MY_CITIES, FROM_ANNEXED_CITIES, FROM_PUPPET_CITIES, FROM_POPULATION, TOTAL
    );

    public static Unhappiness of(String str) {
        StringParser parser = new StringParser();
        return build(parser.parse(str, AVAILABLE_IDENTIFIERS));
    }

    private static Unhappiness build(Map<String, Integer> map) {
        int fromCities = map.getOrDefault(FROM_CITIES, 0);
        int fromMyCities = map.getOrDefault(FROM_MY_CITIES, 0);
        int fromAnnexedCities = map.getOrDefault(FROM_ANNEXED_CITIES, 0);
        int fromPuppetCities = map.getOrDefault(FROM_PUPPET_CITIES, 0);
        int fromPopulation = map.getOrDefault(FROM_POPULATION, 0);

        return Unhappiness.builder()
            .inCities(fromCities)
            .inMyCities(fromMyCities)
            .inAnnexedCities(fromAnnexedCities)
            .inPuppetCities(fromPuppetCities)
            .population(fromPopulation)
            .build();
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
                Cities          |   %5d  |    %5d | %s
                MyCities        |   %5d  |    %5d | %s
                AnnexedCities   |   %5d  |    %5d | %s
                PuppetCities    |   %5d  |    %5d | %s
                Population      |   %5d  |    %5d | %s
                Total           |   %5d  |    %5d | %s
                %n""",

                a.getInCities(), b.getInCities(), cmp(a.getInCities(), b.getInCities()),
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

