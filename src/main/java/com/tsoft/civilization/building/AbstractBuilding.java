package com.tsoft.civilization.building;

import com.tsoft.civilization.L10n.building.L10nBuilding;
import com.tsoft.civilization.building.util.BuildingCatalog;
import com.tsoft.civilization.building.util.BuildingType;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.building.AbstractBuildingView;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.economic.BuildingScore;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.UUID;

public abstract class AbstractBuilding<V extends AbstractBuildingView> implements CanBeBuilt {
    private String id;
    private City city;

    private boolean isDestroyed;

    public abstract BuildingType getBuildingType();
    public abstract BuildingScore getSupply(City city);
    public abstract int getGoldCost();
    public abstract int getStrength();
    public abstract V getView();
    public abstract String getClassUuid();

    // Method of a unit from the catalog (they don't have civilization etc)
    public abstract boolean checkEraAndTechnology(Civilization civilization);

    public static AbstractBuilding newInstance(String classUuid, City city) {
        AbstractBuilding building = BuildingCatalog.values().findByClassUuid(classUuid);
        if (building == null) {
            return null;
        }

        try {
            Constructor constructor = building.getClass().getConstructor();
            building = (AbstractBuilding)constructor.newInstance();
            building.init(city);

            return building;
        } catch (Exception ex) {
            DefaultLogger.severe("Can't create an object", ex);
        }
        return null;
    }

    public static List<AbstractBuilding> getBuildingsCatalog() {
        return BuildingCatalog.values();
    }

    public static AbstractBuilding getBuildingFromCatalogByClassUuid(String classUuid) {
        return BuildingCatalog.values().findByClassUuid(classUuid);
    }

    protected AbstractBuilding() { }

    // Initialization on create the object
    private void init(City city) {
        this.city = city;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    public AbstractTile getTile(Point location) {
        return city.getTilesMap().getTile(location);
    }

    public World getWorld() {
        return city.getWorld();
    }

    public Civilization getCivilization() {
        return city.getCivilization();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractBuilding building = (AbstractBuilding) o;

        if (!id.equals(building.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void remove() {
        isDestroyed = true;

        Event event = new Event(this, L10nBuilding.BUILDING_DESTROYED, Event.INFORMATION);
        getCivilization().addEvent(event);

        city.destroyBuilding(this);
    }
}
