package com.tsoft.civilization.improvement;

import com.tsoft.civilization.L10n.L10n;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.RU;

public class L10nImprovement {
    public static final L10n IMPROVEMENT_NOT_FOUND = new L10n()
        .put("en", "Improvement not found")
        .put("ru", "Улучшение не найдено");

    public static final L10n ANCIENT_RUINS = new L10n()
        .put(EN, "Ancient Ruins")
        .put(RU, "Древние руины");

    public static final L10n ANCIENT_RUINS_DESCRIPTION = new L10n()
        .put(EN, "Farming is one of the earliest and most important of all human professions, as it allowed mankind to stop migrating and settle in one location without depleting the local resources. Farms can be constructed on most any land to improve the hex's output of food.")
        .put(RU, "Ферма одно из самых ранних и наиболее значимых из всех профессий человечества, т.к. оно позволило уменьшить миграции и поселиться в одном месте");

    public static final L10n FARM_NAME = new L10n()
        .put(EN, "Farm")
        .put(RU, "Ферма");

    public static final L10n FARM_DESCRIPTION = new L10n()
        .put(EN, "Farming is one of the earliest and most important of all human professions, as it allowed mankind to stop migrating and settle in one location without depleting the local resources. Farms can be constructed on most any land to improve the hex's output of food.")
        .put(RU, "Ферма одно из самых ранних и наиболее значимых из всех профессий человечества, т.к. оно позволило уменьшить миграции и поселиться в одном месте");

    public static final L10n MINE_NAME = new L10n()
        .put(EN, "Mine")
        .put(RU, "Рудник");

    public static final L10n MINE_DESCRIPTION = new L10n()
        .put(EN, "The Mine grants access to several strategic and luxury resources. It can also be built on hills near cities to improve their Production potential.")
        .put(RU, "Рудник дает доступ к некоторым стратегическим ресурсам и ресурсам роскоши.");

    public static final L10n ROAD_NAME = new L10n()
        .put(EN, "Road")
        .put(RU, "Дорога");

    public static final L10n ROAD_DESCRIPTION = new L10n()
        .put(EN, "Road links cities that gives economical burst")
        .put(RU, "Дороги связывают города приводя к экономическому взрыву");

    /** Actions */
    public static final L10n BUILD_FARM_ACTION = new L10n()
        .put(EN, "Build a Farm")
        .put(RU, "Построить ферму");

    public static final L10n IMPROVEMENT_ALREADY_EXISTS = new L10n()
        .put(EN, "An improvement on this tile already exists")
        .put(RU, "На этой клетке улучшение уже существует");
}
