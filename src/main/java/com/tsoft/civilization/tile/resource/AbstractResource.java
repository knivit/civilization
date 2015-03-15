package com.tsoft.civilization.tile.resource;

import com.tsoft.civilization.util.DefaultLogger;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractResource {
    private static final AbstractResource resources[] = new AbstractResource[] {
        newInstance(Iron.class),
        newInstance(Aluminium.class),
        newInstance(Uranium.class),
        newInstance(Oil.class)
    };

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
        } catch (IllegalAccessException ex1) {
            ex = ex1;
        } catch (InstantiationException ex2) {
            ex = ex2;
        }

        DefaultLogger.severe("Can't instantiate a Resource by its class =" + resourceClass.getName());
        throw new IllegalArgumentException(ex);
    }

    public static List<AbstractResource> getResources() {
        return Arrays.asList(resources);
    }
}
