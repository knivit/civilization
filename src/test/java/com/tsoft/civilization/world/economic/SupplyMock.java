package com.tsoft.civilization.world.economic;

public class SupplyMock {
    public static Supply of(String str) {
        Supply result = Supply.EMPTY_SUPPLY;

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

            result = result.add(getSupply(type, value));
        }

        return result;
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
            case 'M': return Supply.builder().greatMusicians(value).build();
            case 'W': return Supply.builder().greatWriter(value).build();
            case 'R': return Supply.builder().greatMerchant(value).build();
            case 'E': return Supply.builder().greatEngineer(value).build();
            case 'I': return Supply.builder().greatScientist(value).build();
        }

        throw new IllegalArgumentException("Unknown type = " + type);
    }

    public static boolean equals(Supply a, Supply b) {
        boolean isEqual =
            a.getFood() == b.getFood() &&
            a.getProduction() == b.getProduction() &&
            a.getGold() == b.getGold();

        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.printf(
                "Type           | Expected   | Got\n" +
                "--------------------------------------\n" +
                "Food           |     %1$5d  |    %2$5d\n" +
                "Production     |     %3$5d  |    %4$5d\n" +
                "Gold           |     %5$5d  |    %6$5d\n" +
                "Science        |     %7$5d  |    %8$5d\n" +
                "Culture        |     %9$5d  |    %10$5d\n" +
                "Happiness      |     %11$5d  |    %12$5d\n" +
                "Unhappiness    |     %13$5d  |    %14$5d\n" +
                "Population     |     %15$5d  |    %15$5d\n" +

                "greatArtist    |     %17$5d  |    %18$5d\n" +
                "greatMusicians |     %19$5d  |    %20$5d\n" +
                "greatWriter    |     %21$5d  |    %22$5d\n" +
                "greatMerchant  |     %23$5d  |    %24$5d\n" +
                "greatEngineer  |     %25$5d  |    %26$5d\n" +
                "greatScientist |     %27$5d  |    %28$5d\n%n",

                a.getFood(), b.getFood(),
                a.getProduction(), b.getProduction(),
                a.getGold(), b.getGold(),
                a.getScience(), b.getScience(),
                a.getCulture(), b.getCulture(),
                a.getHappiness(), b.getHappiness(),
                a.getUnhappiness(), b.getUnhappiness(),
                a.getPopulation(), b.getPopulation(),

                a.getGreatArtist(), b.getGreatArtist(),
                a.getGreatMusicians(), b.getGreatMusicians(),
                a.getGreatWriter(), b.getGreatWriter(),
                a.getGreatMerchant(), b.getGreatMerchant(),
                a.getGreatEngineer(), b.getGreatEngineer(),
                a.getGreatScientist(), b.getGreatScientist()
            );
        }

        return isEqual;
    }
}
