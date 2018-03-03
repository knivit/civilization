package com.tsoft.civilization.tile.luxury;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractLuxury {
    private static final Logger log = LoggerFactory.getLogger(AbstractLuxury.class);

    private static final AbstractLuxury luxuries[] = new AbstractLuxury[] {
        newInstance(Bananas.class),
        newInstance(Fish.class),
        newInstance(Silk.class),
        newInstance(Sugar.class),
        newInstance(Whales.class),
        newInstance(Wine.class)
    };

    public abstract LuxuryType getLuxuryType();

    public abstract char getAsciiChar();

    public String getName() {
        return getClass().getName();
    }

    public static AbstractLuxury newInstance(Class<? extends AbstractLuxury> luxuryClass) {
        Exception ex;
        try {
            AbstractLuxury luxury = luxuryClass.newInstance();
            return luxury;
        } catch (IllegalAccessException ex1) {
            ex = ex1;
        } catch (InstantiationException ex2) {
            ex = ex2;
        }

        log.error("Can't instantiate a Luxury by its class = {}", luxuryClass.getName());
        throw new IllegalArgumentException(ex);
    }

    public static List<AbstractLuxury> getLuxuries() {
        return Arrays.asList(luxuries);
    }
}
