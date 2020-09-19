package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.unit.AbstractUnit;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

@Slf4j
public abstract class TerrainFeature<V extends AbstractFeatureView> {
    public static final int FEATURE_NOT_INITIALIZED = -1;
    public static final int FEATURE_REMOVED = 0;

    private AbstractTile<?> tile;

    private int strength = FEATURE_NOT_INITIALIZED;

    public abstract TileType getTileType();
    public abstract Supply getSupply();
    public abstract boolean canBuildCity();
    public abstract int getDefensiveBonusPercent();
    public abstract int getMaxStrength();
    public abstract String getClassUuid();
    public abstract V getView();

    public static TerrainFeature<?> newInstance(String classUuid, AbstractTile<?> tile) {
        TerrainFeature<?> feature = FeatureCatalog.findByClassUuid(classUuid);
        if (feature == null) {
            return null;
        }

        try {
            Constructor<?> constructor = feature.getClass().getConstructor();
            feature = (TerrainFeature<?>)constructor.newInstance();
            feature.init(tile);

            return feature;
        } catch (Exception ex) {
            log.error("Can't create an object {}", feature.getClass().getSimpleName(), ex);
        }
        return null;
    }

    protected TerrainFeature() { }

    private void init(AbstractTile<?> tile) {
        this.tile = tile;
        tile.addFeature(this);
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

    public int getPassCost(AbstractUnit unit) {
        return getPassCost(unit.getCivilization(), unit);
    }

    public int getPassCost(Civilization civilization, AbstractUnit unit) {
        return FeaturePassCostTable.get(civilization, unit, this);
    }

    public int getMissilePassCost(HasCombatStrength attacker) {
        return MissileFeaturePastCostTable.get(attacker, this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " { " +
                "strength=" + strength +
                " }";
    }
}
