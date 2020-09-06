package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.tile.feature.TerrainFeatureList;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.feature.*;
import com.tsoft.civilization.tile.luxury.AbstractLuxury;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.tile.base.AbstractTileView;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

@Slf4j
public abstract class AbstractTile<V extends AbstractTileView> {
    private Point location;
    private AbstractLuxury luxury;
    private AbstractResource resource;
    private AbstractImprovement<?> improvement;
    private TerrainFeatureList terrainFeatures = new TerrainFeatureList();

    public abstract TileType getTileType();
    public abstract boolean canBuildCity();

    public abstract int getDefensiveBonusPercent();

    public abstract String getClassUuid();
    public abstract V getView();

    public abstract Supply getBaseSupply();

    public static AbstractTile<?> newInstance(String classUuid) {
        AbstractTile<?> tile = TileCatalog.findByClassUuid(classUuid);
        if (tile == null) {
            return null;
        }

        try {
            Constructor<?> constructor = tile.getClass().getConstructor();
            tile = (AbstractTile<?>)constructor.newInstance();

            return tile;
        } catch (Exception ex) {
            log.error("Can't create an object", ex);
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

    public AbstractImprovement<?> getImprovement() {
        return improvement;
    }

    public void setImprovement(AbstractImprovement<?> improvement) {
        this.improvement = improvement;
    }

    public TerrainFeatureList getTerrainFeatures() {
        return terrainFeatures;
    }

    public <F extends TerrainFeature<?>> F getFeature(Class<F> featureClass) {
        return terrainFeatures.getByClass(featureClass);
    }

    public void addFeature(TerrainFeature<?> feature) {
        terrainFeatures.add(feature);
    }

    public void removeFeature(TerrainFeature<?> feature) {
        terrainFeatures.remove(feature);
    }

    // Don't generate events for civilization
    // They must be generated by caller
    public Supply getSupply() {
        Supply supply = getBaseSupply();

        // add feature's supply
        if (!terrainFeatures.isEmpty()) {
            // start from last (i.e. on top) feature
            for (int i = terrainFeatures.size() - 1; i >= 0; i --) {
                TerrainFeature<?> feature = terrainFeatures.get(i);

                // look for a blocking feature
                if (feature.isBlockingTileSupply()) {
                    return feature.getSupply();
                }

                supply = supply.add(feature.getSupply());
            }
        }
        return supply;
    }

    // Returns passing cost for a unit
    public int getPassCost(AbstractUnit<?> unit) {
        return getPassCost(unit.getCivilization(), unit);
    }

    public int getPassCost(Civilization civilization, AbstractUnit<?> unit) {
        int passCost = TilePassCostTable.get(civilization, unit, this);
        if (terrainFeatures.isEmpty()) {
            return passCost;
        }

        // add features starting from the uppermost
        for (int i = terrainFeatures.size() - 1; i >= 0; i --) {
            TerrainFeature<?> feature = terrainFeatures.get(i);

            int featurePassCost = feature.getPassCost(unit);
            if (featurePassCost == TilePassCostTable.UNPASSABLE) {
                return TilePassCostTable.UNPASSABLE;
            }

            passCost += featurePassCost;
        }

        return passCost;
    }

    // Returns passing (or flying) cost for attacker's missile
    public int getMissilePastCost(HasCombatStrength attacker) {
        int passCost = MissileTilePastCostTable.get(attacker, this);
        if (!terrainFeatures.isEmpty()) {
            // start from last (i.e. on top) feature
            for (int i = terrainFeatures.size() - 1; i >= 0; i --) {
                TerrainFeature<?> feature = terrainFeatures.get(i);

                int featurePassCost = feature.getMissilePassCost(attacker);
                if (featurePassCost == TilePassCostTable.UNPASSABLE) {
                    return TilePassCostTable.UNPASSABLE;
                }

                passCost += featurePassCost;
            }
        }
        return passCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractTile<?> that = (AbstractTile<?>) o;
        return location.equals(that.location);
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
