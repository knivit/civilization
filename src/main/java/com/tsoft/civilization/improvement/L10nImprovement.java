package com.tsoft.civilization.improvement;

import com.tsoft.civilization.L10n.L10n;

public class L10nImprovement {
    public static final L10n FARM_NAME = new L10n()
        .put("en", "Farm")
        .put("ru", "Ферма");

    public static final L10n FARM_DESCRIPTION = new L10n()
        .put("en", "Farming is one of the earliest and most important of all human professions, as it allowed mankind to stop migrating and settle in one location without depleting the local resources. Farms can be constructed on most any land to improve the hex's output of food.")
        .put("ru", "Ферма одно из самых ранних и наиболее значимых из всех профессий человечества, т.к. оно позволило уменьшить миграции и поселиться в одном месте");

    public static final L10n MINE_NAME = new L10n()
        .put("en", "Mine")
        .put("ru", "Рудник");

    public static final L10n MINE_DESCRIPTION = new L10n()
        .put("en", "The Mine grants access to several strategic and luxury resources. It can also be built on hills near cities to improve their Production potential.")
        .put("ru", "Рудник дает доступ к некоторым стратегическим ресурсам и ресурсам роскоши.");

    public static final L10n ROAD_NAME = new L10n()
            .put("en", "Road")
            .put("ru", "Дорога");

    public static final L10n ROAD_DESCRIPTION = new L10n()
            .put("en", "Road links cities that gives economical burst")
            .put("ru", "Дороги связывают города приводя к экономическому взрыву");

    /** Economics */
    public static final L10n IMPROVEMENT_EXPENSES_SUPPLY = new L10n()
            .put("en", "Improvement service expenses")
            .put("ru", "Расходы на обслуживания здания");

    public static final L10n IMPROVEMENT_SUPPLY = new L10n()
            .put("en", "Improvement's production")
            .put("ru", "Продукция здания");

    /** Actions */
    public static final L10n BUILD_FARM_ACTION = new L10n()
        .put("en", "Build a Farm")
        .put("ru", "Построить ферму");

    public static final L10n IMPROVEMENT_ALREADY_EXISTS = new L10n()
        .put("en", "An improvement on this tile already exists")
        .put("ru", "На этой клетке улучшение уже существует");

}
