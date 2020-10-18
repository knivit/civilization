package com.tsoft.civilization.L10n;

public class L10nImprovement {
    public static L10nMap FARM_NAME = new L10nMap()
        .set("en", "Farm")
        .set("ru", "Ферма");

    public static L10nMap FARM_DESCRIPTION = new L10nMap()
        .set("en", "Farming is one of the earliest and most important of all human professions, as it allowed mankind to stop migrating and settle in one location without depleting the local resources. Farms can be constructed on most any land to improve the hex's output of food.")
        .set("ru", "Ферма одно из самых ранних и наиболее значимых из всех профессий человечества, т.к. оно позволило уменьшить миграции и поселиться в одном месте");

    public static L10nMap ROAD_NAME = new L10nMap()
            .set("en", "Road")
            .set("ru", "\u0414\u043e\u0440\u043e\u0433\u0430");

    public static L10nMap ROAD_DESCRIPTION = new L10nMap()
            .set("en", "Road links cities that gives economical burst")
            .set("ru", "\u0414\u043e\u0440\u043e\u0433\u0438 \u0441\u0432\u044f\u0437\u044b\u0432\u0430\u044e\u0442 \u0433\u043e\u0440\u043e\u0434\u0430 \u043f\u0440\u0438\u0432\u043e\u0434\u044f \u043a \u044d\u043a\u043e\u043d\u043e\u043c\u0438\u0447\u0435\u0441\u043a\u043e\u043c\u0443 \u0432\u0437\u0440\u044b\u0432\u0443");

    /** Economics */
    public static L10nMap IMPROVEMENT_EXPENSES_SUPPLY = new L10nMap()
            .set("en", "Improvement service expenses")
            .set("ru", "\u0420\u0430\u0441\u0445\u043e\u0434\u044b \u043d\u0430 \u043e\u0431\u0441\u043b\u0443\u0436\u0438\u0432\u0430\u043d\u0438\u044f \u0437\u0434\u0430\u043d\u0438\u044f");

    public static L10nMap IMPROVEMENT_SUPPLY = new L10nMap()
            .set("en", "Improvement's production")
            .set("ru", "\u041f\u0440\u043e\u0434\u0443\u043a\u0446\u0438\u044f \u0437\u0434\u0430\u043d\u0438\u044f");

    /** Actions */
    public static L10nMap BUILD_FARM_ACTION = new L10nMap()
        .set("en", "Build a Farm")
        .set("ru", "Построить ферму");

    public static L10nMap IMPROVEMENT_ALREADY_EXISTS = new L10nMap()
        .set("en", "An improvement on this tile already exists")
        .set("ru", "На этой клетке улучшение уже существует");

}
