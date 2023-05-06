package com.tsoft.civilization.civilization.building;

import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class L10nBuilding {

    /* Categories */
    public static final L10n BUILDING_CATEGORY = new L10n()
        .put(EN, "A building")
        .put(RU, "Здание");

    public static final L10n NATIONAL_WONDER_CATEGORY = new L10n()
        .put(EN, "A national wonder")
        .put(RU, "Чудеса нации");

    public static final L10n WORLD_WONDER_CATEGORY = new L10n()
        .put(EN, "A world wonder")
        .put(RU, "Чудеса света");

    /* Events */
    public static final L10n BUILDING_DESTROYED = new L10n()
        .put(EN, "Building $buildingName has been destroyed")
        .put(RU, "Здание $buildingName разрушено");

    /* Actions */
    public static final L10n DESTROY = new L10n()
        .put(EN, "Destroy")
        .put(RU, "Разрушить");

    public static final L10n DESTROY_DESCRIPTION = new L10n()
        .put(EN, "Destroy the building")
        .put(RU, "Разрушить здание");

    public static final L10n BUILDING_NOT_FOUND = new L10n()
        .put(EN, "Building not found")
        .put(RU, "Здание не найдено");

    public static final L10n NO_BUILDINGS = new L10n()
        .put(EN, "No Buildings")
        .put(RU, "Зданий нет");

    public static final L10n NO_CONSTRUCTIONS = new L10n()
        .put(EN, "No constructions")
        .put(RU, "Нет строительства");

    public static final L10n BUILDINGS_TO_CONSTRUCT = new L10n()
        .put(EN, "Buildings To Construct")
        .put(RU, "Здания для постройки");

    public static final L10n UNITS_TO_CONSTRUCT = new L10n()
        .put(EN, "Units To Construct")
        .put(RU, "Юниты для создания");

    public static final L10n BUILD = new L10n()
        .put(EN, "Build")
        .put(RU, "Построить");

    public static final L10n BUILD_DESCRIPTION = new L10n()
        .put(EN, "Construct the building")
        .put(RU, "Построить здание");

    public static final L10n BUY = new L10n()
        .put(EN, "Buy")
        .put(RU, "Купить");

    public static final L10n BUILDING_LIST = new L10n()
        .put(EN, "Buildings in the City")
        .put(RU, "Здания города");

    public static final L10n CATEGORY = new L10n()
        .put(EN, "Category")
        .put(RU, "Категория");

    public static final L10n CONSTRUCTION_LIST = new L10n()
        .put(EN, "Construction")
        .put(RU, "Строительство");

    public static final L10n CONSTRUCTION_TURNS_LABEL = new L10n()
        .put(EN, "Turns")
        .put(RU, "Ходов");

    public static final L10n CONSTRUCTION_PRIORITY_LABEL = new L10n()
        .put(EN, "Priority")
        .put(RU, "Приоритет");

    public static final L10n NAME = new L10n()
        .put(EN, "Name")
        .put(RU, "Название");
}
