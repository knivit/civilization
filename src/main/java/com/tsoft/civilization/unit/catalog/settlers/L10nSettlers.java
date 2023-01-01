package com.tsoft.civilization.unit.catalog.settlers;

import com.tsoft.civilization.util.l10n.L10n;

public class L10nSettlers {
    public static final L10n BUILD_CITY_NAME = new L10n()
            .put("en", "Build a city")
            .put("ru", "Построить город");

    public static final L10n BUILD_CITY_DESCRIPTION = new L10n()
            .put("en", "Cities have a Combat Strength and can defend against attacks on their own. A unit can also be Garrisoned inside to increase that strength (adding a portion of its own strength).")
            .put("ru", "Города могут самостоятельно защищаться от нападений. Юнит может встать гарнизоном в городе, что также увеличит силу города.");

    public static final L10n CITY_BUILT = new L10n()
            .put("en", "A new city is built")
            .put("ru", "Основан новый город");

    public static final L10n CAN_BUILD_CITY = new L10n()
            .put("en", "A city can be build here")
            .put("ru", "Здесь может быть построен город");

    public static final L10n CANT_BUILD_CITY = new L10n()
        .put("en", "A city can not be build here")
        .put("ru", "Здесь неможет быть построен город");

    public static final L10n CANT_BUILD_CITY_TILE_IS_OCCUPIED = new L10n()
            .put("en", "Can't build a city because this tile is occupied")
            .put("ru", "Нельзя построить город, потому что эта ячейка занята");

    public static final L10n CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY = new L10n()
            .put("en", "A new city must be as far as 4 tiles from another city")
            .put("ru", "Новый город должен быть не ближе 4 ячеек от другого города");
}
