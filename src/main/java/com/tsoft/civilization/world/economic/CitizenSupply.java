package com.tsoft.civilization.world.economic;

public class CitizenSupply {
    private int food;

    public CitizenSupply(int food) {
        this.food = food;
    }

    public int getFood() {
        return food;
    }

    public void add(CitizenSupply supply) {
        food += supply.food;
    }
}
