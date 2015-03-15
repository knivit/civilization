package com.tsoft.civilization.tile.luxury;

public class Bananas extends AbstractLuxury {
    @Override
    public LuxuryType getLuxuryType() {
        return LuxuryType.FOOD;
    }

    @Override
    public char getAsciiChar() {
        return 'b';
    }
}
