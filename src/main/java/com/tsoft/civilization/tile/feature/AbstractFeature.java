package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.tile.util.FeaturePassCostTable;
import com.tsoft.civilization.tile.util.MissileFeaturePastCostTable;
import com.tsoft.civilization.tile.util.TileCatalog;
import com.tsoft.civilization.world.economic.TileSupply;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.web.view.tile.feature.AbstractFeatureView;

import java.lang.reflect.Constructor;
import java.util.List;

public abstract class AbstractFeature<V extends AbstractFeatureView> {
    public static final int FEATURE_NOT_INITIALIZED = -1;
    public static final int FEATURE_REMOVED = 0;

    private AbstractTile tile;

    private int strength = FEATURE_NOT_INITIALIZED;

    public abstract TileType getTileType();
    public abstract TileSupply getSupply();
    public abstract boolean canBuildCity();
    public abstract int getDefensiveBonusPercent();
    public abstract int getMaxStrength();
    public abstract String getClassUuid();
    public abstract V getView();

    public static AbstractFeature newInstance(String classUuid, AbstractTile tile) {
        AbstractFeature feature = getFeatureFromCatalogByClassUuid(classUuid);
        if (feature == null) {
            return null;
        }

        try {
            Constructor constructor = feature.getClass().getConstructor();
            feature = (AbstractFeature)constructor.newInstance();
            feature.init(tile);

            return feature;
        } catch (Exception ex) {
            DefaultLogger.severe("Can't create an object", ex);
        }
        return null;
    }

    protected AbstractFeature() { }

    private void init(AbstractTile tile) {
        this.tile = tile;
        tile.addFeature(this);
    }

    public static AbstractFeature getFeatureFromCatalogByClassUuid(String classUuid) {
        for (AbstractFeature feature : TileCatalog.features()) {
            if (feature.getClassUuid().equals(classUuid)) {
                return feature;
            }
        }
        return null;
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
        return FeaturePassCostTable.get(unit, this);
    }

    public int getMissilePassCost(HasCombatStrength attacker) {
        return MissileFeaturePastCostTable.get(attacker, this);
    }

    @Override
    public String toString() {
        return getClass().getName() + " {" +
                "tile=" + tile.toString() +
                ", strength=" + strength +
                '}';
    }
}
