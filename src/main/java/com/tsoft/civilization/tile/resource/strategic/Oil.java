package com.tsoft.civilization.tile.resource.strategic;

import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.ResourceType;

public class Oil extends AbstractResource {
    @Override
    public ResourceType getResourceType() {
        return ResourceType.EARTH_SEA;
    }

    @Override
    public char getAsciiChar() {
        return 'o';
    }
}
