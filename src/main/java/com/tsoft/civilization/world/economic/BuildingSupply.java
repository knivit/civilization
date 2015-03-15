package com.tsoft.civilization.world.economic;

public class BuildingSupply {
    private int food;
    private int production;
    private int gold;
    private int science;
    private int culture;
    private int happiness;

    public BuildingSupply(int food, int production, int gold, int science, int culture, int happiness) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.science = science;
        this.culture = culture;
        this.happiness = happiness;
    }

    int getFood() {
        return food;
    }

    int getProduction() {
        return production;
    }

    int getGold() {
        return gold;
    }

    public int getScience() {
        return science;
    }

    public int getCulture() {
        return culture;
    }

    public int getHappiness() {
        return happiness;
    }

    void add(BuildingSupply supply) {
        food += supply.food;
        production += supply.production;
        gold += supply.gold;
        science += supply.science;
        culture += supply.culture;
        happiness += supply.happiness;
    }
}
