package com.tsoft.civilization.world.economic;

public class TileSupply {
    private int food;
    private int production;
    private int gold;

    public TileSupply(int food, int production, int gold) {
        this.food = food;
        this.production = production;
        this.gold = gold;
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

    void add(TileSupply supply) {
        food += supply.food;
        production += supply.production;
        gold += supply.gold;
    }
}
