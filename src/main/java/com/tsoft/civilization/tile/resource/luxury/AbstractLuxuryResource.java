package com.tsoft.civilization.tile.resource.luxury;

import com.tsoft.civilization.tile.resource.bonus.Bananas;
import com.tsoft.civilization.tile.resource.bonus.Fish;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public abstract class AbstractLuxuryResource {

    private static final AbstractLuxuryResource luxuries[] = new AbstractLuxuryResource[] {
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

    public static AbstractLuxuryResource newInstance(Class<? extends AbstractLuxuryResource> luxuryClass) {
        try {
            return luxuryClass.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            log.error("Can't instantiate a Luxury resource by its class = {}", luxuryClass.getSimpleName());
            throw new IllegalArgumentException(ex);
        }
    }

    public static List<AbstractLuxuryResource> getLuxuries() {
        return Arrays.asList(luxuries);
    }
}
