package com.tsoft.civilization.building;

import com.tsoft.civilization.L10n.L10n;

public class L10nBuilding {
    public static final L10n CAN_DESTROY = new L10n()
            .put("en", "The building can be destroyed")
            .put("ru", "Здание может быть разрушено");

    public static final L10n CANT_DESTROY = new L10n()
            .put("en", "The building can't be destroyed")
            .put("ru", "Здание не может быть разрушено");

    public static final L10n BUILDING_DESTROYED = new L10n()
            .put("en", "Building %s has been destroyed")
            .put("ru", "Здание %s разрушено");

    public static final L10n DESTROY = new L10n()
            .put("en", "Destroy")
            .put("ru", "Разрушить");

    public static final L10n DESTROY_DESCRIPTION = new L10n()
            .put("en", "Destroy the building")
            .put("ru", "Разрушить здание");

    public static final L10n BUILDING_NOT_FOUND = new L10n()
            .put("en", "Building not found")
            .put("ru", "Здание не найдено");

    public static final L10n NO_BUILDINGS = new L10n()
            .put("en", "No Buildings")
            .put("ru", "Зданий нет");

    public static final L10n NO_CONSTRUCTIONS = new L10n()
        .put("en", "No Constructions")
        .put("ru", "Нет строительства");

    public static final L10n BUILDINGS_TO_CONSTRUCT = new L10n()
            .put("en", "Buildings To Construct")
            .put("ru", "Здания для постройки");

    public static final L10n UNITS_TO_CONSTRUCT = new L10n()
            .put("en", "Units To Construct")
            .put("ru", "Юниты для создания");

    public static final L10n BUILD = new L10n()
            .put("en", "Build")
            .put("ru", "Построить");

    public static final L10n BUILD_DESCRIPTION = new L10n()
            .put("en", "Construct the building")
            .put("ru", "Построить здание");

    public static final L10n BUY = new L10n()
            .put("en", "Buy")
            .put("ru", "Купить");

    public static final L10n BUY_DESCRIPTION = new L10n()
            .put("en", "Buy a building")
            .put("ru", "Купить здание");

    public static final L10n BUILDING_LIST = new L10n()
            .put("en", "Buildings in the City")
            .put("ru", "Здания города");

    public static final L10n CONSTRUCTION_LIST = new L10n()
        .put("en", "Construction in progress")
        .put("ru", "Строительство");

    public static final L10n NAME = new L10n()
            .put("en", "Name")
            .put("ru", "Название");

    /** Actions */

    public static final L10n BUILDING_WAS_BOUGHT = new L10n()
            .put("en", "A Building was bought")
            .put("ru", "Здание было куплено");

    /** Economics */

    public static final L10n BUILDING_EXPENSES_SUPPLY = new L10n()
            .put("en", "Building service expenses")
            .put("ru", "Расходы на обслуживания здания");

    public static final L10n BUILDING_SUPPLY = new L10n()
            .put("en", "Building's production")
            .put("ru", "Продукция здания");

    public static final L10n BUILDING_CONSTRUCTION_EXPENSES = new L10n()
            .put("en", "Building's construction expenses")
            .put("ru", "Расходы на постройку здания");
}
