package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractFeature {
    public static final int FEATURE_NOT_INITIALIZED = -1;
    public static final int FEATURE_REMOVED = 0;

    private AbstractTile tile;

    private int strength = FEATURE_NOT_INITIALIZED;

    public abstract TileType getTileType();
    public abstract Supply getSupply();
    public abstract boolean canBuildCity();
    public abstract int getDefensiveBonusPercent();
    public abstract int getMaxStrength();
    public abstract String getClassUuid();
    public abstract AbstractFeatureView getView();

    protected AbstractFeature() { }

    public void init(AbstractTile tile) {
        this.tile = tile;
        tile.addFeature(this);
    }

    public AbstractTile getBaseTile() {
        return tile;
    }

    public boolean isBlockingTileSupply() {
        return false;
    }

    public int getStrength() {
        if (strength == FEATURE_NOT_INITIALIZED) {
            strength = getMaxStrength();
        }
        return strength;
    }

    public void addStrength(int strength) {
        this.strength = getStrength() + strength;
        if (this.strength <= FEATURE_REMOVED) {
            this.strength = FEATURE_REMOVED;
            tile.removeFeature(this);
        }
    }

    public boolean isRemoved() {
        return getStrength() == FEATURE_REMOVED;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " { " +
                "strength=" + strength +
                " }";
    }
}
