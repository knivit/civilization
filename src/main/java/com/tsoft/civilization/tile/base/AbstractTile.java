package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.tile.util.TerrainFeatureList;
import com.tsoft.civilization.tile.util.MissileTilePastCostTable;
import com.tsoft.civilization.tile.util.TileCatalog;
import com.tsoft.civilization.tile.util.TilePassCostTable;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.feature.*;
import com.tsoft.civilization.tile.luxury.AbstractLuxury;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.tile.base.AbstractTileView;

import java.lang.reflect.Constructor;

public abstract class AbstractTile<V extends AbstractTileView> {
    private Point location;
    private AbstractLuxury luxury;
    private AbstractResource resource;
    private AbstractImprovement improvement;
    private TerrainFeatureList terrainFeatures = new TerrainFeatureList();

    public abstract TileType getTileType();
    public abstract boolean canBuildCity();

    public abstract int getDefensiveBonusPercent();

    public abstract String getClassUuid();
    public abstract V getView();

    public abstract Supply getBaseSupply();

    public static AbstractTile newInstance(String classUuid) {
        AbstractTile tile = getTileFromCatalogByClassUuid(classUuid);
        if (tile == null) {
            return null;
        }

        try {
            Constructor constructor = tile.getClass().getConstructor();
            tile = (AbstractTile)constructor.newInstance();

            return tile;
        } catch (Exception ex) {
            DefaultLogger.severe("Can't create an object", ex);
        }
        return null;
    }

    public static AbstractTile getTileFromCatalogByClassUuid(String classUuid) {
        for (AbstractTile tile : TileCatalog.baseTiles()) {
            if (tile.getClassUuid().equals(classUuid)) {
                return tile;
            }
        }
        return null;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public AbstractLuxury getLuxury() {
        return luxury;
    }

    public boolean setLuxury(AbstractLuxury luxury) {
        if (getResource() == null) {
            this.luxury = luxury;
            return true;
        }
        return false;
    }

    public AbstractResource getResource() {
        return resource;
    }

    public boolean setResource(AbstractResource resource) {
        if (getLuxury() == null) {
            this.resource = resource;
            return true;
        }
        return false;
    }

    public AbstractImprovement getImprovement() {
        return improvement;
    }

    public void setImprovement(AbstractImprovement improvement) {
        this.improvement = improvement;
    }

    public TerrainFeatureList getTerrainFeatures() {
        return terrainFeatures;
    }

    public <F extends TerrainFeature> F getFeature(Class<F> featureClass) {
        F feature = terrainFeatures.getByClass(featureClass);
        return feature;
    }

    public void addFeature(TerrainFeature feature) {
        terrainFeatures.add(feature);
    }

    public void removeFeature(TerrainFeature feature) {
        terrainFeatures.remove(feature);
    }

    // Don't generate events for civilization
    // They must be generated by caller
    public Supply getSupply() {
        Supply supply = new Supply();
        supply.add(getBaseSupply());

        // add feature's supply
        if (!terrainFeatures.isEmpty()) {
            // start from last (i.e. on top) feature
            for (int i = terrainFeatures.size() - 1; i >= 0; i --) {
                TerrainFeature feature = terrainFeatures.get(i);

                // look for a blocking feature
                if (feature.isBlockingTileSupply()) {
                    supply = new Supply();
                    supply.add(feature.getSupply());
                    return supply;
                }

                supply.add(feature.getSupply());
            }
        }
        return supply;
    }

    // Returns passing cost for a unit
    public int getPassCost(AbstractUnit unit) {
        int passCost = TilePassCostTable.get(unit, this);
        if (!terrainFeatures.isEmpty()) {
            // start from last (i.e. on top) feature
            for (int i = terrainFeatures.size() - 1; i >= 0; i --) {
                TerrainFeature feature = terrainFeatures.get(i);
                passCost += feature.getPassCost(unit);
            }
        }
        return passCost;
    }

    // Returns passing (or flying) cost for attacker's missile
    public int getMissilePastCost(HasCombatStrength attacker) {
        int passCost = MissileTilePastCostTable.get(attacker, this);
        if (!terrainFeatures.isEmpty()) {
            // start from last (i.e. on top) feature
            for (int i = terrainFeatures.size() - 1; i >= 0; i --) {
                TerrainFeature feature = terrainFeatures.get(i);
                passCost += feature.getMissilePassCost(attacker);
            }
        }
        return passCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractTile that = (AbstractTile) o;

        if (!location.equals(that.location)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
