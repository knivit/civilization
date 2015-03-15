package com.tsoft.civilization.world.economic;

public class CitySupply {
    private int food;
    private int production;
    private int gold;
    private int population;

    public CitySupply(int food, int production, int gold, int population) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.population = population;
    }

    public int getFood() {
        return food;
    }

    public int getProduction() {
        return production;
    }

    public int getGold() {
        return gold;
    }

    public int getPopulation() {
        return population;
    }

    void add(CitySupply supply) {
        food += supply.food;
        production += supply.production;
        gold += supply.gold;
        population += supply.population;
    }
}
