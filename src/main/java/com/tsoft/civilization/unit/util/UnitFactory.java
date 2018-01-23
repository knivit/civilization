package com.tsoft.civilization.unit.util;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.unit.UnitType;
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Civilization;

public class UnitFactory {
    private static UnitCollection unitCatalog = new UnitList();

    static {
        for (UnitType unitType : UnitType.values()) {
            unitCatalog.add(createUnit(unitType));
        }
    }

    private UnitFactory() { }

    public static <T extends AbstractUnit> T newInstance(T unit, Civilization civilization, Point location) {
        TilesMap tilesMap = civilization.getTilesMap();

        // If the given location is invalid, change it to (0, 0)
        if ((location.getX() < 0 || location.getX() >= tilesMap.getWidth()) ||
                (location.getY() < 0 || location.getY() >= tilesMap.getHeight())) {
            DefaultLogger.severe("Invalid location " + location.toString() + ", must be [0.." + (tilesMap.getWidth() - 1) + ",0.." + (tilesMap.getHeight() - 1) + "]", new IllegalArgumentException());

            location = new Point(0, 0);
        }

        T clone = (T)createUnit(unit.getUnitType());
        clone.init(civilization, location);

        return clone;
    }

    public static UnitCollection getUnitCatalog() {
        return unitCatalog;
    }

    public static AbstractUnit getUnitFromCatalogByClassUuid(String classUuid) {
        return unitCatalog.findByClassUuid(classUuid);
    }

    private static AbstractUnit createUnit(UnitType unitType) {
        switch (unitType) {
            case ARCHERS: return new Archers();
            case GREAT_ARTIST: return new GreatArtist();
            case GREAT_ENGINEER: return new GreatEngineer();
            case GREAT_GENERAL: return new GreatGeneral();
            case GREAT_MERCHANT: return new GreatMerchant();
            case GREAT_SCIENTIST: return new GreatScientist();
            case SETTLERS: return new Settlers();
            case WARRIORS: return new Warriors();
            case WORKERS: return new Workers();
            default: throw new IllegalArgumentException("Unknown unit type " + unitType);
        }
    }
}
