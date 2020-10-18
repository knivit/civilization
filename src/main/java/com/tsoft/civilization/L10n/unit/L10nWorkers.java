package com.tsoft.civilization.L10n.unit;

import com.tsoft.civilization.L10n.L10nMap;

public class L10nWorkers {

    public static L10nMap REMOVE_FOREST_NAME = new L10nMap()
            .set("en", "Remove Forest")
            .set("ru", "\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u043b\u0435\u0441");

    public static L10nMap REMOVE_FOREST_DESCRIPTION = new L10nMap()
            .set("en", "The forest will be removed from he tile")
            .set("ru", "\u041b\u0435\u0441 \u0431\u0443\u0434\u0435\u0442 \u0443\u0434\u0430\u043b\u0435\u043d \u0441 \u044d\u0442\u043e\u0439 \u044f\u0447\u0435\u0439\u043a\u0438");

    public static L10nMap REMOVE_HILL_NAME = new L10nMap()
            .set("en", "Remove Hill")
            .set("ru", "\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0445\u043e\u043b\u043c");

    public static L10nMap REMOVE_HILL_DESCRIPTION = new L10nMap()
            .set("en", "The hill will be removed. The Mining technology must be open.")
            .set("ru", "\u0425\u043e\u043b\u043c \u0431\u0443\u0434\u0435\u0442 \u0443\u0434\u0430\u043b\u0435\u043d. \u0422\u0435\u0445\u043d\u043e\u043b\u043e\u0433\u0438\u044f \u043e\u0431\u044b\u0447\u0438 \u0434\u043e\u043b\u0436\u043d\u0430 \u0431\u044b\u0442\u044c \u0438\u0441\u0441\u043b\u0435\u0434\u043e\u0432\u0430\u043d\u0430.");

    /** Actions */
    public static L10nMap CAN_BUILD_IMPROVEMENT = new L10nMap()
        .set("en", "Improvement %s can be built")
        .set("ru", "Улучшение %s может быть построено");

    public static L10nMap BUILDING_IMPROVEMENT = new L10nMap()
        .set("en", "Building improvement %s")
        .set("ru", "Строится улучшение %s");

    public static L10nMap IMPROVEMENT_IS_BUILT = new L10nMap()
        .set("en", "Improvement %s is built")
        .set("ru", "Улучшение %s построено");

    public static L10nMap CAN_REMOVE_FOREST = new L10nMap()
            .set("en", "Forest can be removed")
            .set("ru", "\u041b\u0435\u0441 \u043c\u043e\u0436\u0435\u0442 \u0431\u044b\u0442\u044c \u0432\u044b\u0440\u0443\u0431\u043b\u0435\u043d");

    public static L10nMap CAN_REMOVE_HILL = new L10nMap()
            .set("en", "Hill can be removed")
            .set("ru", "\u0425\u043e\u043b\u043c \u043c\u043e\u0436\u0435\u0442 \u0431\u044b\u0442\u044c \u0443\u0434\u0430\u043b\u0435\u043d");

    public static L10nMap FOREST_IS_REMOVED = new L10nMap()
            .set("en", "Forest is removed")
            .set("ru", "\u041b\u0435\u0441 \u0432\u044b\u0440\u0443\u0431\u043b\u0435\u043d");

    public static L10nMap HILL_IS_REMOVED = new L10nMap()
            .set("en", "Hill is removed")
            .set("ru", "\u0425\u043e\u043b\u043c \u0443\u0434\u0430\u043b\u0435\u043d");

    public static L10nMap REMOVING_FOREST = new L10nMap()
            .set("en", "Workers are removing the forest")
            .set("ru", "\u0420\u0430\u0431\u043e\u0447\u0438\u0435 \u0432\u044b\u0440\u0443\u0431\u0430\u044e\u0442 \u043b\u0435\u0441");

    public static L10nMap REMOVING_HILL = new L10nMap()
            .set("en", "Workers are removing the hill")
            .set("ru", "\u0420\u0430\u0431\u043e\u0447\u0438\u0435 \u0443\u0434\u0430\u043b\u044f\u044e\u0442 \u0445\u043e\u043b\u043c");

    public static L10nMap FAIL_NO_MINING_TECHNOLOGY = new L10nMap()
            .set("en", "Mining must be opened")
            .set("ru", "\u0414\u043e\u043b\u0436\u043d\u0430 \u0431\u044b\u0442\u044c \u043e\u0442\u043a\u0440\u044b\u0442\u0430 \u0414\u043e\u0431\u044b\u0447\u0430 \u0440\u0443\u0434\u044b");

    public static L10nMap FAIL_NO_FOREST_HERE = new L10nMap()
            .set("en", "There is no forest to remove")
            .set("ru", "\u041d\u0435\u0442 \u043b\u0435\u0441\u0430 \u0434\u043b\u044f \u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f");

    public static L10nMap FAIL_NO_HILL_HERE = new L10nMap()
            .set("en", "There is no hill to remove")
            .set("ru", "\u0417\u0434\u0435\u0441\u044c \u043d\u0435\u0442\u0443 \u0445\u043e\u043b\u043c\u0430 \u0434\u043b\u044f \u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f");

    public static L10nMap FAIL_FOREST_MUST_BE_REMOVED_FIRST = new L10nMap()
            .set("en", "Forest must be removed first")
            .set("ru", "\u0421\u043d\u0430\u0447\u0430\u043b\u0430 \u0434\u043e\u043b\u0436\u0435\u043d \u0431\u044b\u0442\u044c \u0432\u044b\u0440\u0443\u0431\u043b\u0435\u043d \u043b\u0435\u0441");
}
