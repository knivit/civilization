package com.tsoft.civilization.economic;

public class SupplyMock {
    public static Supply of(String str) {
        Supply supply = Supply.EMPTY_SUPPLY;

        int i = 0;
        char type = '\0';
        boolean typeMode = true;
        while (i < str.length()) {
            if (typeMode) {
                type = str.charAt(i);
                if (type != ' ') typeMode = false;
                i ++;

                continue;
            }

            int k = i + 1;
            int sign = 1;
            if (k < str.length() && str.charAt(k) == '-') {
                sign = -1;
                k ++;
            }

            while (k < str.length() && str.charAt(k) >= '0' && str.charAt(k) <= '9') k++;

            int value = sign * Integer.parseInt(str.substring(i, k));
            i = k;
            typeMode = true;

            supply = supply.add(getSupply(type, value));
        }

        return supply;
    }

    private static Supply getSupply(char type, int value) {
        switch (type) {
            case 'F': return Supply.builder().food(value).build();
            case 'P': return Supply.builder().production(value).build();
            case 'G': return Supply.builder().gold(value).build();
            case 'S': return Supply.builder().science(value).build();
            case 'C': return Supply.builder().culture(value).build();
            case 'H': return Supply.builder().happiness(value).build();
            case 'U': return Supply.builder().unhappiness(value).build();
            case 'O': return Supply.builder().population(value).build();

            case 'A': return Supply.builder().greatArtist(value).build();
            case 'M': return Supply.builder().greatMusician(value).build();
            case 'W': return Supply.builder().greatWriter(value).build();
            case 'R': return Supply.builder().greatMerchant(value).build();
            case 'E': return Supply.builder().greatEngineer(value).build();
            case 'I': return Supply.builder().greatScientist(value).build();
        }

        throw new IllegalArgumentException("Unknown type = " + type);
    }

    public static boolean equals(String expectedSupply, Supply actualSupply) {
        return equals(SupplyMock.of(expectedSupply), actualSupply);
    }

    public static boolean equals(Supply a, Supply b) {
        boolean isEqual =
            a.getFood() == b.getFood() &&
            a.getProduction() == b.getProduction() &&
            a.getGold() == b.getGold() &&
            a.getScience() == b.getScience() &&
            a.getCulture() == b.getCulture() &&
            a.getHappiness() == b.getHappiness() &&
            a.getUnhappiness() == b.getUnhappiness() &&
            a.getPopulation() == b.getPopulation() &&
            a.getGreatArtist() == b.getGreatArtist() &&
            a.getGreatMusician() == b.getGreatMusician() &&
            a.getGreatWriter() == b.getGreatWriter() &&
            a.getGreatMerchant() == b.getGreatMerchant() &&
            a.getGreatEngineer() == b.getGreatEngineer() &&
            a.getGreatScientist() == b.getGreatScientist();

        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.printf(
                "Type            | Expected   | Actual\n" +
                "-----------------------------------------\n" +
                "Food            |     %5d  |    %5d | %s\n" +
                "Production      |     %5d  |    %5d | %s\n" +
                "Gold            |     %5d  |    %5d | %s\n" +
                "Science         |     %5d  |    %5d | %s\n" +
                "Culture         |     %5d  |    %5d | %s\n" +
                "Happiness       |     %5d  |    %5d | %s\n" +
                "Unhappiness     |     %5d  |    %5d | %s\n" +
                "Population      |     %5d  |    %5d | %s\n" +

                "Great Artist    |     %5d  |    %5d | %s\n" +
                "Great Musicians |     %5d  |    %5d | %s\n" +
                "Great Writer    |     %5d  |    %5d | %s\n" +
                "Great Merchant  |     %5d  |    %5d | %s\n" +
                "Great Engineer  |     %5d  |    %5d | %s\n" +
                "Great Scientist |     %5d  |    %5d | %s\n%n",

                a.getFood(), b.getFood(), cmp(a.getFood(), b.getFood()),
                a.getProduction(), b.getProduction(), cmp(a.getProduction(), b.getProduction()),
                a.getGold(), b.getGold(), cmp(a.getGold(), b.getGold()),
                a.getScience(), b.getScience(), cmp(a.getScience(), b.getScience()),
                a.getCulture(), b.getCulture(), cmp(a.getCulture(), b.getCulture()),
                a.getHappiness(), b.getHappiness(), cmp(a.getHappiness(), b.getHappiness()),
                a.getUnhappiness(), b.getUnhappiness(), cmp(a.getUnhappiness(), b.getUnhappiness()),
                a.getPopulation(), b.getPopulation(), cmp(a.getPopulation(), b.getPopulation()),

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
}
