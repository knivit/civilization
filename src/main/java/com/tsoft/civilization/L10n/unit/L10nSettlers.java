package com.tsoft.civilization.L10n.unit;

import com.tsoft.civilization.L10n.L10nMap;

public class L10nSettlers {
    public static L10nMap BUILD_CITY_NAME = new L10nMap()
            .set("en", "Build a city")
            .set("ru", "\u041f\u043e\u0441\u0442\u0440\u043e\u0438\u0442\u044c \u0433\u043e\u0440\u043e\u0434");

    public static L10nMap BUILD_CITY_DESCRIPTION = new L10nMap()
            .set("en", "Cities have a Combat Strength and can defend against attacks on their own. A unit can also be Garrisoned inside to increase that strength (adding a portion of its own strength).")
            .set("ru", "\u0413\u043e\u0440\u043e\u0434\u0430 \u043c\u043e\u0433\u0443\u0442 \u0441\u0430\u043c\u043e\u0441\u0442\u043e\u044f\u0442\u0435\u043b\u044c\u043d\u043e \u0437\u0430\u0449\u0438\u0449\u0430\u0442\u044c\u0441\u044f \u043e\u0442 \u043d\u0430\u043f\u0430\u0434\u0435\u043d\u0438\u0439. \u042e\u043d\u0438\u0442 \u043c\u043e\u0436\u0435\u0442 \u0432\u0441\u0442\u0430\u0442\u044c \u0433\u0430\u0440\u043d\u0438\u0437\u043e\u043d\u043e\u043c \u0432 \u0433\u043e\u0440\u043e\u0434\u0435, \u0447\u0442\u043e \u0442\u0430\u043a\u0436\u0435 \u0443\u0432\u0435\u043b\u0438\u0447\u0438\u0442 \u0441\u0438\u043b\u0443 \u0433\u043e\u0440\u043e\u0434\u0430.");

    public static L10nMap CITY_BUILT = new L10nMap()
            .set("en", "A new city is built")
            .set("ru", "\u041e\u0441\u043d\u043e\u0432\u0430\u043d \u043d\u043e\u0432\u044b\u0439 \u0433\u043e\u0440\u043e\u0434");

    public static L10nMap CAN_BUILD_CITY = new L10nMap()
            .set("en", "A city can be build here")
            .set("ru", "\u0417\u0434\u0435\u0441\u044c \u043c\u043e\u0436\u0435\u0442 \u0431\u044b\u0442\u044c \u043f\u043e\u0441\u0442\u0440\u043e\u0435\u043d \u0433\u043e\u0440\u043e\u0434");

    public static L10nMap CANT_BUILD_CITY_TILE_IS_OCCUPIED = new L10nMap()
            .set("en", "Can't build a city because this tile is occupied")
            .set("ru", "\u041d\u0435\u043b\u044c\u0437\u044f \u043f\u043e\u0441\u0442\u0440\u043e\u0438\u0442\u044c \u0433\u043e\u0440\u043e\u0434, \u043f\u043e\u0442\u043e\u043c\u0443 \u0447\u0442\u043e \u044d\u0442\u0430 \u044f\u0447\u0435\u0439\u043a\u0430 \u0437\u0430\u043d\u044f\u0442\u0430");

    public static L10nMap CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY = new L10nMap()
            .set("en", "A new city must be as far as 4 tiles from another city")
            .set("ru", "\u041d\u043e\u0432\u044b\u0439 \u0433\u043e\u0440\u043e\u0434 \u0434\u043e\u043b\u0436\u0435\u043d \u0431\u044b\u0442\u044c \u043d\u0435 \u0431\u043b\u0438\u0436\u0435 4 \u044f\u0447\u0435\u0435\u043a \u043e\u0442 \u0434\u0440\u0443\u0433\u043e\u0433\u043e \u0433\u043e\u0440\u043e\u0434\u0430");
}
