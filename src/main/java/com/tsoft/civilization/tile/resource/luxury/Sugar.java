package com.tsoft.civilization.tile.resource.luxury;

public class Sugar extends AbstractLuxuryResource {
    @Override
    public LuxuryType getLuxuryType() {
        return LuxuryType.FOOD;
    }

    @Override
    public char getAsciiChar() {
        return 'g';
    }
}
