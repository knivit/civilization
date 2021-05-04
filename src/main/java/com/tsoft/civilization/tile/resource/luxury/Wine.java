package com.tsoft.civilization.tile.resource.luxury;

public class Wine extends AbstractLuxuryResource {
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
