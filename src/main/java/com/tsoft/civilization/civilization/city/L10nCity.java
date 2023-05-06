package com.tsoft.civilization.civilization.city;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.util.l10n.L10nList;
import com.tsoft.civilization.util.l10n.L10nMap;
import com.tsoft.civilization.civilization.L10nCivilization;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class L10nCity {

    /** Web */

    public static final L10n BUSINESS_FEATURES = new L10n()
        .put(EN, "Business Features")
        .put(RU, "Деловые характеристики");

    public static final L10n COMBAT_FEATURES = new L10n()
        .put(EN, "Combat Features")
        .put(RU, "Военные характеристики");

    public static final L10n CITIZEN = new L10n()
        .put(EN, "Number of Citizens")
        .put(RU, "Количество жителей");

    public static final L10n CITIZEN_HEADER = new L10n()
        .put(EN, "C")
        .put(RU, "Н");

    public static final L10n CITY_ON_TILE = new L10n()
        .put(EN, "City on the Tile")
        .put(RU, "Город на ячейке");

    /** Actions */

    public static final L10n NO_CITIES = new L10n()
        .put(EN, "No Cities")
        .put(RU, "Городов нет");

    public static final L10n NAME = new L10n()
        .put(EN, "Name")
        .put(RU, "Название");

    public static final L10n BUY_TILE = new L10n()
        .put(EN, "Buy a tile")
        .put(RU, "Купить ячейку");

    public static final L10n BUY_TILE_DESCRIPTION = new L10n()
        .put(EN, "Buy a tile to expand your city's territory")
        .put(RU, "Купить ячейку для расширения территории города");

    /** Events */

    public static final L10n NEW_CITY_EVENT = new L10n()
        .put(EN, "A new city $cityName was founded")
        .put(RU, "Основан новый $cityName город");

    public static final L10n CITY_DESTROYED_EVENT = new L10n()
        .put(EN, "City $cityName was destroyed")
        .put(RU, "Город $cityName разрушен");

    public static final L10n CITY_WAS_CAPTURED = new L10n()
        .put(EN, "City $cityName was captured by $winner")
        .put(RU, "Город $cityName был захвачен $winner");

    public static final L10n UNIT_HAS_CAPTURED_CITY = new L10n()
        .put(EN, "A unit $unitName has captured the city $cityName")
        .put(RU, "Юнит $unitName захватил город $cityName");

    public static final L10n NEW_BUILDING_BUILT_EVENT = new L10n()
        .put(EN, "A new building $buildingName has been constructed")
        .put(RU, "Было построено новое здание $buildingName");

    public static final L10n NEW_UNIT_BUILT_EVENT = new L10n()
        .put(EN, "A new unit $unitName has been constructed")
        .put(RU, "Был построен новый юнит $unitName");

    public static final L10n CITY_STOP_YEAR_EVENT = new L10n()
        .put(EN, "City $cityName stop year: originalSupply=$originalSupply, incomeSupply=$incomeSupply, outcomeSupply=$outcomeSupply, totalSupply=$totalSupply")
        .put(RU, "Город $cityName завершил год: на начало года=$originalSupply, поступило=$incomeSupply, потрачено=$outcomeSupply, итог=$totalSupply");

    /** Population */

    public static final L10n STARVATION_STARTED_EVENT = new L10n()
        .put(EN, "All food is consumed, the starvation has started in city $cityName")
        .put(RU, "Пища закончилась, начался голод в городе $cityName");

    public static final L10n STARVATION_ENDED_EVENT = new L10n()
        .put(EN, "The starvation has ended in the city $cityName")
        .put(RU, "В городе $cityName закончился голод");

    public static final L10n CITIZEN_WAS_BORN_EVENT = new L10n()
        .put(EN, "A citizen was born in city $cityName")
        .put(RU, "Родился житель in city $cityName");

    public static final L10n CITIZEN_HAS_DIED_EVENT = new L10n()
        .put(EN, "A citizen has died in city $cityName")
        .put(RU, "Умер житель в городе $cityName");

    public static final L10n TILE_BOUGHT_EVENT = new L10n()
        .put(EN, "City $cityName bought a tile at price $gold")
        .put(RU, "Город $cityName купил ячейку по цене $gold");

    /** Statistic */

    public static final L10n POPULATION = new L10n()
        .put(EN, "Population")
        .put(RU, "Население");

    public static final L10n DEFENSE_STRENGTH = new L10n()
        .put(EN, "Defense Strength")
        .put(RU, "Мощь обороны");

    public static final L10n PRODUCTION = new L10n()
        .put(EN, "Production")
        .put(RU, "Производство");

    public static final L10n GOLD = new L10n()
        .put(EN, "Gold")
        .put(RU, "Золото");

    public static final L10n FOOD = new L10n()
        .put(EN, "Food")
        .put(RU, "Пища");

    public static final L10n HAPPINESS = new L10n()
        .put(EN, "Happiness")
        .put(RU, "Настроение");

    /** Cities */

    public static final L10n MOSCOW = new L10n()
        .put(EN, "Moscow")
        .put(RU, "Москва");

    public static final L10n SAINT_PETERSBURG = new L10n()
        .put(EN, "Saint Petersburg")
        .put(RU, "Санкт-Петерсбург");

    public static final L10n YEKATERINBURG = new L10n()
        .put(EN, "Yekaterinburg")
        .put(RU, "Екатеринбург");

    public static final L10n WASHINGTON = new L10n()
        .put(EN, "Washington")
        .put(RU, "Вашингтон");

    public static final L10n NEW_YORK = new L10n()
        .put(EN, "New York")
        .put(RU, "Нью-Йорк");

    public static final L10n CHICAGO = new L10n()
        .put(EN, "Chicago")
        .put(RU, "Чикаго");

    public static final L10n TOKYO = new L10n()
        .put(EN, "Tokyo")
        .put(RU, "Токио");

    public static L10nMap<L10n, L10nList> CITIES = new L10nMap<L10n, L10nList>()
        .put(L10nCivilization.RUSSIA, new L10nList()
            .add(L10nCity.MOSCOW)
            .add(L10nCity.SAINT_PETERSBURG)
            .add(L10nCity.YEKATERINBURG)
        )
        .put(L10nCivilization.AMERICA, new L10nList()
            .add(L10nCity.WASHINGTON)
            .add(L10nCity.NEW_YORK)
            .add(L10nCity.CHICAGO)
        )
        .put(L10nCivilization.JAPAN, new L10nList()
            .add(L10nCity.TOKYO)
        );
}
