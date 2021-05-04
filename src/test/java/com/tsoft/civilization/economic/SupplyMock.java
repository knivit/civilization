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
            case 'H': throw new IllegalArgumentException("fix");
            case 'U': throw new IllegalArgumentException("fix");
            case 'O': throw new IllegalArgumentException("fix");

            case 'A': return Supply.builder().greatArtist(value).build();
            case 'M': return Supply.builder().greatMusician(value).build();
            case 'W': return Supply.builder().greatWriter(value).build();
            case 'R': return Supply.builder().greatMerchant(value).build();
            case 'E': return Supply.builder().greatEngineer(value).build();
            case 'I': return Supply.builder().greatScientist(value).build();
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

            a.getGreatArtist() == b.getGreatArtist() &&
            a.getGreatMusician() == b.getGreatMusician() &&
            a.getGreatWriter() == b.getGreatWriter() &&
            a.getGreatMerchant() == b.getGreatMerchant() &&
            a.getGreatEngineer() == b.getGreatEngineer() &&
            a.getGreatScientist() == b.getGreatScientist();

        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.printf("""
                Type            |   Actual | Expected
                -----------------------------------------
                Food            |     %5d  |    %5d | %s
                Production      |     %5d  |    %5d | %s
                Gold            |     %5d  |    %5d | %s
                Science         |     %5d  |    %5d | %s
                Culture         |     %5d  |    %5d | %s
                Great Artist    |     %5d  |    %5d | %s
                Great Musicians |     %5d  |    %5d | %s
                Great Writer    |     %5d  |    %5d | %s
                Great Merchant  |     %5d  |    %5d | %s
                Great Engineer  |     %5d  |    %5d | %s
                Great Scientist |     %5d  |    %5d | %s
                %n""",

                a.getFood(), b.getFood(), cmp(a.getFood(), b.getFood()),
                a.getProduction(), b.getProduction(), cmp(a.getProduction(), b.getProduction()),
                a.getGold(), b.getGold(), cmp(a.getGold(), b.getGold()),
                a.getScience(), b.getScience(), cmp(a.getScience(), b.getScience()),
                a.getCulture(), b.getCulture(), cmp(a.getCulture(), b.getCulture()),

                a.getGreatArtist(), b.getGreatArtist(), cmp(a.getGreatArtist(), b.getGreatArtist()),
                a.getGreatMusician(), b.getGreatMusician(), cmp(a.getGreatMusician(), b.getGreatMusician()),
                a.getGreatWriter(), b.getGreatWriter(), cmp(a.getGreatWriter(), b.getGreatWriter()),
                a.getGreatMerchant(), b.getGreatMerchant(), cmp(a.getGreatMerchant(), b.getGreatMerchant()),
                a.getGreatEngineer(), b.getGreatEngineer(), cmp(a.getGreatEngineer(), b.getGreatEngineer()),
                a.getGreatScientist(), b.getGreatScientist(), cmp(a.getGreatScientist(), b.getGreatScientist())
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
