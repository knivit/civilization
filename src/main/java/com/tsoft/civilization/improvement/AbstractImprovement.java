package com.tsoft.civilization.improvement;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.economic.Supply;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode(of = "id")
public abstract class AbstractImprovement {
    private final String id = UUID.randomUUID().toString();

    protected Civilization civilization;
    protected Point location;

    public abstract Supply getSupply();
    public abstract CombatStrength getBaseCombatStrength();
    public abstract AbstractImprovementView getView();

    public AbstractImprovement(Civilization civilization, Point location) {
        Objects.requireNonNull(civilization, "civilization can't be null");
        Objects.requireNonNull(location, "location can't be null");

        this.civilization = civilization;
        this.location = location;

        getTilesMap().getTile(location).setImprovement(this);
    }

    public String getId() {
        return id;
    }

    public World getWorld() {
        return civilization.getWorld();
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public TilesMap getTilesMap() {
        return getWorld().getTilesMap();
    }

    public Point getLocation() {
        return location;
    }

    public boolean isBlockingTileSupply() {
        return false;
    }

    @Override
    public String toString() {
        return getClass().getName() + " {location=" + location + '}';
    }
}
