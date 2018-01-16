package com.tsoft.civilization.tile.resource;

import com.tsoft.civilization.util.DefaultLogger;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractResource {
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

        DefaultLogger.severe("Can't instantiate a Resource by its class =" + resourceClass.getName());
        throw new IllegalArgumentException(ex);
    }
}
