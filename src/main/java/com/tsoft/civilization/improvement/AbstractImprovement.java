package com.tsoft.civilization.improvement;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.improvement.AbstractImprovementView;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.economic.ImprovementScore;

import java.util.UUID;

public abstract class AbstractImprovement<V extends AbstractImprovementView> {
    private String id;

    protected Civilization civilization;
    protected Point location;

    public abstract ImprovementScore getSupply();
    public abstract CombatStrength getBaseCombatStrength();
    public abstract V getView();

    public AbstractImprovement(Civilization civilization, Point location) {
        this.civilization = civilization;
        this.location = location;

        id = UUID.randomUUID().toString();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractImprovement that = (AbstractImprovement) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getName() + " {location=" + location.toString() + '}';
    }
}
