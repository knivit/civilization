package com.tsoft.civilization.building;

import com.tsoft.civilization.improvement.city.City;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

@Slf4j
public final class BuildingFactory {

    private BuildingFactory() { }

    public static AbstractBuilding newInstance(String classUuid, City city) {
        AbstractBuilding building = BuildingCatalog.findByClassUuid(classUuid);
        if (building == null) {
            throw new IllegalArgumentException("Unknown building classUuid = " + classUuid);
        }

        try {
            Constructor<?> constructor = building.getClass().getConstructor(City.class);
            building = (AbstractBuilding)constructor.newInstance(city);

            return building;
        } catch (Exception ex) {
            log.error("Error on newInstance of {}", building.getClass().getSimpleName(), ex);
            throw new IllegalStateException(ex);
        }
    }
}
