package com.tsoft.civilization.tile.resource.bonus;

import com.tsoft.civilization.tile.resource.luxury.AbstractLuxuryResource;
import com.tsoft.civilization.tile.resource.luxury.LuxuryType;

public class Bananas extends AbstractLuxuryResource {
    @Override
    public LuxuryType getLuxuryType() {
        return LuxuryType.FOOD;
    }

    @Override
    public char getAsciiChar() {
        return 'b';
    }
}
