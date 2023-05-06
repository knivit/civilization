package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public final class CityActionResults {

    private CityActionResults() { }

    public static final ActionFailureResult NOT_ENOUGH_MONEY = new ActionFailureResult(new L10n()
        .put(EN, "Not enough money")
        .put(RU, "Недостаточно денег"));

    public static final ActionFailureResult WRONG_ERA_OR_TECHNOLOGY = new ActionFailureResult(new L10n()
        .put(EN, "Wrong Era or insufficient civilization's technology level")
        .put(RU, "Неверная эра или уровень развития цивилизации недостаточен"));

    public static final ActionFailureResult CITY_NOT_FOUND = new ActionFailureResult(new L10n()
        .put(EN, "City not found")
        .put(RU, "Город не найден"));
}
