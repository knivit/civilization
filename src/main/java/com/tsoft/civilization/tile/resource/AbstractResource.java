package com.tsoft.civilization.tile.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractResource {
    private static final Logger log = LoggerFactory.getLogger(AbstractResource.class);

    public abstract ResourceType getResourceType();

    public abstract char getAsciiChar();

    public String getName() {
        return getClass().getName();
    }

    public static AbstractResource newInstance(Class<? extends AbstractResource> resourceClass) {
        Exception ex;
        try {
            AbstractResource resource = resourceClass.newInstance();
            return resource;
        } catch (Exception ex1) {
            ex = ex1;
        }

        log.error("Can't instantiate a Resource by its class = {}", resourceClass.getName());
        throw new IllegalArgumentException(ex);
    }
}
