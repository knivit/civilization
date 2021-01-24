package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.tile.base.grassland.Grassland;
import com.tsoft.civilization.tile.base.ocean.Ocean;
import com.tsoft.civilization.tile.base.tundra.Tundra;
import com.tsoft.civilization.tile.feature.TerrainFeatureList;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.feature.*;
import com.tsoft.civilization.tile.luxury.AbstractLuxury;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.util.Point;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractTile {
    private Point location;
    private AbstractLuxury luxury;
    private AbstractResource resource;
    private AbstractImprovement improvement;

    private final TerrainFeatureList terrainFeatures = new TerrainFeatureList();

    public abstract TileType getTileType();
    public abstract boolean canBuildCity();

    public abstract int getDefensiveBonusPercent();

    public abstract String getClassUuid();
    public abstract AbstractTileView getView();

    public abstract Supply getBaseSupply();

    public static AbstractTile newInstance(String classUuid) {
        AbstractTile tile = TileCatalog.findByClassUuid(classUuid);
        if (tile == null) {
            return null;
        }

        try {
            Constructor<?> constructor = tile.getClass().getConstructor();
            tile = (AbstractTile)constructor.newInstance();

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
        return terrainFeatures.getByClass(featureClass);
    }

    @SafeVarargs
    public final boolean hasFeature(Class<? extends TerrainFeature> ... features) {
        if (features != null) {
            for (Class<? extends TerrainFeature> feature : features) {
                if (terrainFeatures.getByClass(feature) != null) {
                    return true;
                }
            }
        }

        return false;
    }

    public void addFeature(TerrainFeature feature) {
        terrainFeatures.add(feature);
    }

    public void removeFeature(TerrainFeature feature) {
        terrainFeatures.remove(feature);
    }

    public boolean isOcean() {
        return isIn(Ocean.class);
    }

    public boolean isTundra() {
        return isIn(Tundra.class);
    }

    public boolean isGrassland() {
        return isIn(Grassland.class);
    }

    @SafeVarargs
    public final boolean isIn(Class<? extends AbstractTile> ... classes) {
        if (classes != null) {
            for (Class<? extends AbstractTile> clazz : classes) {
                if (clazz.equals(getClass())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractTile that = (AbstractTile) o;
        return location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public String toString() {
        String features = terrainFeatures.stream().map(e -> e.getClass().getSimpleName()).collect(Collectors.joining(", "));
        return getClass().getSimpleName() + location +
            ", features=[" + features + "]" +
            ", improvement=" + improvement +
            ", luxury=" + luxury +
            ", resource=" + resource;
    }
}
