package com.tsoft.civilization.tile.resource;

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
