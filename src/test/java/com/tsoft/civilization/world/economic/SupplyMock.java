package com.tsoft.civilization.world.economic;

public class SupplyMock extends Supply {
    public SupplyMock(String str) {
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

            setValue(type, value);
        }
    }

    private void setValue(char type, int value) {
        switch (type) {
            case 'F': { setFood(value); return; }
            case 'P': { setProduction(value); return; }
            case 'G': { setGold(value); return; }
            case 'S': { setScience(value); return; }
            case 'C': { setCulture(value); return; }
            case 'H': { setHappiness(value); return; }
            case 'O': { setPopulation(value); return; }
        }

        throw new IllegalArgumentException("Unknown type = " + type);
    }

    @Override
    public SupplyMock setFood(int food) {
        super.setFood(food);
        return this;
    }

    @Override
    public SupplyMock setProduction(int production) {
        super.setProduction(production);
        return this;
    }

    @Override
    public SupplyMock setGold(int gold) {
        super.setGold(gold);
        return this;
    }

    @Override
    public SupplyMock setScience(int science) {
        super.setScience(science);
        return this;
    }

    @Override
    public SupplyMock setCulture(int culture) {
        super.setCulture(culture);
        return this;
    }

    @Override
    public SupplyMock setHappiness(int happiness) {
        super.setHappiness(happiness);
        return this;
    }

    @Override
    public SupplyMock setPopulation(int population) {
        super.setPopulation(population);
        return this;
    }

    public boolean isEqualTo(Supply supply) {
        boolean isEqual = getFood() == supply.getFood() &&
                getProduction() == supply.getProduction() &&
                getGold() == supply.getGold();

        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.println(String.format(
                "Type       | Expected   | Got\n" +
                "----------------------------------\n" +
                "Food       |     %1$5d  |    %2$5d\n" +
                "Production |     %3$5d  |    %4$5d\n" +
                "Gold       |     %5$5d  |    %6$5d\n" +
                "Science    |     %7$5d  |    %8$5d\n" +
                "Culture    |     %9$5d  |    %10$5d\n" +
                "Happiness  |     %11$5d  |    %12$5d\n" +
                "Population |     %13$5d  |    %14$5d\n",
                getFood(), supply.getFood(),
                getProduction(), supply.getProduction(),
                getGold(), supply.getGold(),
                getScience(), supply.getScience(),
                getCulture(), supply.getCulture(),
                getHappiness(), supply.getHappiness(),
                getPopulation(), supply.getPopulation()
            ));
        }

        return isEqual;
    }
}
