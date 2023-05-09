package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public final class CityTileActionResults {

    private CityTileActionResults() { }

    public static final ActionSuccessResult CAN_BUY_TILE = new ActionSuccessResult(new L10n()
        .put(EN, "Can buy this tile")
        .put(RU, "Можно купить эту ячейку"));

    public static final ActionSuccessResult CANT_BUY_TILE = new ActionSuccessResult(new L10n()
        .put(EN, "Can not buy this tile")
        .put(RU, "Нельзя купить эту ячейку"));

    public static final ActionSuccessResult TILE_BOUGHT = new ActionSuccessResult(new L10n()
        .put(EN, "The tile is bought")
        .put(RU, "Ячейка куплена"));

    public static final ActionSuccessResult ALREADY_MINE = new ActionSuccessResult(new L10n()
        .put(EN, "The city already has this tile")
        .put(RU, "Город уже владеет этой ячейкой"));

    public static final ActionFailureResult INVALID_CITY = new ActionFailureResult(new L10n()
        .put(EN, "Invalid city")
        .put(RU, "Неверный город"));

    public static final ActionFailureResult INVALID_LOCATION = new ActionFailureResult(new L10n()
        .put(EN, "Invalid location")
        .put(RU, "Неверная позиция"));

    public static final ActionFailureResult NOT_ENOUGH_GOLD = new ActionFailureResult(new L10n()
        .put(EN, "Not enough gold to buy the tile")
        .put(RU, "Недостаточно золота для покупки ячейки"));

    public static final ActionFailureResult NO_TILES_TO_BUY = new ActionFailureResult(new L10n()
        .put(EN, "No tiles to buy")
        .put(RU, "Нет ячеек для покупки"));
}
