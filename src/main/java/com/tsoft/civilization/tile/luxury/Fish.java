package com.tsoft.civilization.tile.luxury;

public class Fish extends AbstractLuxury {
    @Override
    public LuxuryType getLuxuryType() {
        return LuxuryType.SEA_FOOD;
    }

    @Override
    public char getAsciiChar() {
        return 'f';
    }
}
