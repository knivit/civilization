package com.tsoft.civilization.world.economic;

public class ImprovementSupply {
    private int food;
    private int production;
    private int gold;
    private int happiness;

    public ImprovementSupply(int food, int production, int gold, int happiness) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.happiness = happiness;
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

    public int getHappiness() {
        return happiness;
    }

    void add(ImprovementSupply supply) {
        food += supply.food;
        production += supply.production;
        gold += supply.gold;
        happiness += supply.happiness;
    }
}
