package com.tsoft.civilization.tile.resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractResource {
    public abstract ResourceType getResourceType();

    public abstract char getAsciiChar();

    public String getName() {
        return getClass().getName();
    }

    public static AbstractResource newInstance(Class<? extends AbstractResource> resourceClass) {
        try {
            return resourceClass.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            log.error("Can't instantiate a Resource by its class = {}", resourceClass.getSimpleName());
            throw new IllegalArgumentException(ex);
        }
    }
}
