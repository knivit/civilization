package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.util.l10n.L10n;

public class L10nFeature {

    public static final L10n FEATURE_NOT_FOUND = new L10n()
            .put("en", "Feature not found")
            .put("ru", "Свойство не найдено");

    public static final L10n FEATURES = new L10n()
            .put("en", "Business Features")
            .put("ru", "Деловые характеристики");

    /** Economics */
    public static final L10n FEATURE_BLOCKING_SUPPLY = new L10n()
            .put("en", "Feature's blocking supply")
            .put("ru", "Поставки блокирующего свойства местности");

    public static final L10n FEATURE_ON_TILE_SUPPLY = new L10n()
            .put("en", "Feature's on the tile supply")
            .put("ru", "Поставки свойства местности в зависимости от ячейки");
}
