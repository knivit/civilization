package com.tsoft.civilization.tile.luxury;

public class Sugar extends AbstractLuxury {
    @Override
    public LuxuryType getLuxuryType() {
        return LuxuryType.FOOD;
    }

    @Override
    public char getAsciiChar() {
        return 'g';
    }
}
