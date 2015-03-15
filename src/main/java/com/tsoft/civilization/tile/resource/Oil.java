package com.tsoft.civilization.tile.resource;

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
