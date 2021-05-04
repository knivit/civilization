package com.tsoft.civilization.tile.resource.bonus;

import com.tsoft.civilization.tile.resource.luxury.AbstractLuxuryResource;
import com.tsoft.civilization.tile.resource.luxury.LuxuryType;

public class Fish extends AbstractLuxuryResource {
    @Override
    public LuxuryType getLuxuryType() {
        return LuxuryType.SEA_FOOD;
    }

    @Override
    public char getAsciiChar() {
        return 'f';
    }
}
