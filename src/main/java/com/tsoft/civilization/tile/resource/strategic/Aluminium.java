package com.tsoft.civilization.tile.resource.strategic;

import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.ResourceType;

public class Aluminium extends AbstractResource {
    @Override
    public ResourceType getResourceType() {
        return ResourceType.EARTH;
    }

    @Override
    public char getAsciiChar() {
        return 'l';
    }
}
