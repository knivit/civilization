package com.tsoft.civilization.tile.resource;

public class Uranium extends AbstractResource {
    @Override
    public ResourceType getResourceType() {
        return ResourceType.EARTH;
    }

    @Override
    public char getAsciiChar() {
        return 'u';
    }
}
