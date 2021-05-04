package com.tsoft.civilization.tile.resource.luxury;

public class Whales extends AbstractLuxuryResource {
    @Override
    public LuxuryType getLuxuryType() {
        return LuxuryType.SEA_FOOD;
    }

    @Override
    public char getAsciiChar() {
        return 'w';
    }
}
