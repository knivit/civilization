package com.tsoft.civilization.world.economic;

public class Supply {
    private int food;
    private int production;
    private int gold;
    private int science;
    private int culture;
    private int happiness;
    private int population;

    public Supply() { }

    public int getFood() {
        return food;
    }

    public Supply setFood(int food) {
        this.food = food;
        return this;
    }

    public int getProduction() {
        return production;
    }

    public Supply setProduction(int production) {
        this.production = production;
        return this;
    }

    public int getGold() {
        return gold;
    }

    public Supply setGold(int gold) {
        this.gold = gold;
        return this;
    }

    public int getScience() {
        return science;
    }

    public Supply setScience(int science) {
        this.science = science;
        return this;
    }

    public int getCulture() {
        return culture;
    }

    public Supply setCulture(int culture) {
        this.culture = culture;
        return this;
    }

    public int getHappiness() {
        return happiness;
    }

    public Supply setHappiness(int happiness) {
        this.happiness = happiness;
        return this;
    }

    public int getPopulation() {
        return population;
    }

    public Supply setPopulation(int population) {
        this.population = population;
        return this;
    }

    public void add(Supply supply) {
        food += supply.food;
        production += supply.production;
        gold += supply.gold;
        science += supply.science;
        culture += supply.culture;
        happiness += supply.happiness;
        population += supply.population;
    }

    @Override
    public String toString() {
        return "{" +
                "food=" + food +
                ", production=" + production +
                ", gold=" + gold +
                ", science=" + science +
                ", culture=" + culture +
                ", happiness=" + happiness +
                ", population=" + population +
                '}';
    }
}
