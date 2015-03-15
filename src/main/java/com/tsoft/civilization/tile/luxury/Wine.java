package com.tsoft.civilization.tile.luxury;

public class Wine extends AbstractLuxury {
    private LuxuryType luxuryType;

    @Override
    public LuxuryType getLuxuryType() {
        return LuxuryType.FOOD;
    }

    @Override
    public char getAsciiChar() {
        return 'i';
    }
}
