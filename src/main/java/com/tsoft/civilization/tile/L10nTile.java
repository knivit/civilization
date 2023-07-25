package com.tsoft.civilization.tile;

import com.tsoft.civilization.util.l10n.L10n;

public class L10nTile {
    public static final L10n YES = new L10n()
            .put("en", "Yes")
            .put("ru", "Да");

    public static final L10n NO = new L10n()
            .put("en", "No")
            .put("ru", "Нет");

    public static final L10n SELECTED_TILE = new L10n()
            .put("en", "Selected Tile")
            .put("ru", "Выбрана ячейка");

    public static final L10n TILE_SUPPLY = new L10n()
            .put("en", "Tile Supply")
            .put("ru", "Поставки ячейки");

    public static final L10n CAN_BUILD_CITY = new L10n()
            .put("en", "Can build city here ?")
            .put("ru", "Можно построить город ?");

    public static final L10n DEFENSE_BONUS = new L10n()
            .put("en", "Defense Bonus, %")
            .put("ru", "Бонус к защите, %");

    public static final L10n TOTAL = new L10n()
            .put("en", "Total")
            .put("ru", "Итого");

    public static final L10n UNIT_TYPE = new L10n()
            .put("en", "Unit Type")
            .put("ru", "Тип юнита");

    public static final L10n PASS_COST = new L10n()
            .put("en", "Pass Cost")
            .put("ru", "Очки передвижения");

    public static final L10n TILE_IS_UNPASSABLE = new L10n()
            .put("en", "Unpassable")
            .put("ru", "Непроходимо");

    public static final L10n FEATURES = new L10n()
            .put("en", "Business Features")
            .put("ru", "Деловые характеристики");

    /** Tiles */

    public static final L10n DESERT_NAME = new L10n()
            .put("en", "Desert")
            .put("ru", "Пустыня");

    public static final L10n DESERT_DESCRIPTION = new L10n()
            .put("en", "No water, no food, difficult to pass")
            .put("ru", "Нет воды, нет пищи, сложно пройти");

    public static final L10n GRASSLAND_NAME = new L10n()
            .put("en", "Grassland")
            .put("ru", "Поле");

    public static final L10n GRASSLAND_DESCRIPTION = new L10n()
            .put("en", "It is easy to live here")
            .put("ru", "Здесь легко жить");

    public static final L10n LAKE_NAME = new L10n()
            .put("en", "Lake")
            .put("ru", "Озеро");

    public static final L10n LAKE_DESCRIPTION = new L10n()
            .put("en", "Just a lake")
            .put("ru", "Просто озеро");

    public static final L10n OCEAN_NAME = new L10n()
            .put("en", "Ocean")
            .put("ru", "Океан");

    public static final L10n OCEAN_DESCRIPTION = new L10n()
            .put("en", "Astronomy must be opened to allow Embarked Units enter the Ocean")
            .put("ru", "Должна быть открыта Астрономия, чтобы юниты могли пересекать океан");

    public static final L10n PLAIN_NAME = new L10n()
            .put("en", "Plain")
            .put("ru", "Равнина");

    public static final L10n PLAIN_DESCRIPTION = new L10n()
            .put("en", "Plains are good to attack units located on them. They are difficult to defense units.")
            .put("ru", "Равнины хороши для нападения; защищаться на них сложнее, чем, например, в лесу или на холмах");

    public static final L10n SNOW_NAME = new L10n()
            .put("en", "Snow")
            .put("ru", "Снег");

    public static final L10n SNOW_DESCRIPTION = new L10n()
            .put("en", "Snow tiles almost useless from food's point of view. Sometimes they contains resources.")
            .put("ru", "Заснеженные равнины практически бесполезны с точки зрения пищи. Иногда на них расположены полезные ресурсы.");

    public static final L10n TUNDRA_NAME = new L10n()
            .put("en", "Tundra")
            .put("ru", "Тундра");

    public static final L10n TUNDRA_DESCRIPTION = new L10n()
            .put("en", "Foodless")
            .put("ru", "Мало пищи");

    /** Economic */

    public static final L10n TILE_BASE_SUPPLY = new L10n()
            .put("en", "Tile's supply of food, production and gold")
            .put("ru", "Поставки пищи, продукции и золота ячейкой");
}
