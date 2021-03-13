package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.L10n.L10nList;
import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.civilization.L10nCivilization;

public class L10nCity {
    /** Web */

    public static final L10n BUSINESS_FEATURES = new L10n()
            .put("en", "Business Features")
            .put("ru", "Деловые характеристики");

    public static final L10n COMBAT_FEATURES = new L10n()
            .put("en", "Combat Features")
            .put("ru", "Военные характеристики");

    public static final L10n CITIZEN = new L10n()
            .put("en", "Number of Citizens")
            .put("ru", "Количество жителей");

    public static final L10n CITIZEN_HEADER = new L10n()
            .put("en", "C")
            .put("ru", "Н");

    public static final L10n CITY_ON_TILE = new L10n()
            .put("en", "City on the Tile")
            .put("ru", "Город на ячейке");

    /** Actions */

    public static final L10n NO_CITIES = new L10n()
            .put("en", "No Cities")
            .put("ru", "Городов нет");

    public static final L10n CITY_NAME = new L10n()
            .put("en", "City")
            .put("ru", "Город");

    public static final L10n CITY_DESCRIPTION = new L10n()
            .put("en", "Cities are source of power of your civilization")
            .put("ru", "Города - источник военной мощи вашей цивилизации");

    public static final L10n CANT_BUILD_BUILDING_OTHER_ACTION_IN_PROGRESS = new L10n()
            .put("en", "Can't start a building as there is other action is in progress")
            .put("ru", "Нельзя начать строительство, потому что выполняется другое действие");

    public static final L10n CANT_BUILD_THIS_BUILDING = new L10n()
            .put("en", "Can't build this building")
            .put("ru", "Невозможно построить это здание");

    public static final L10n CANT_BUILD_THIS_UNIT = new L10n()
            .put("en", "Can't build this unit")
            .put("ru", "Невозможно построить этот юнит");

    public static final L10n CANT_BUY_THIS_BUILDING = new L10n()
            .put("en", "Can't buy this building")
            .put("ru", "Невозможно купить это здание");

    public static final L10n CAN_BUY_THIS_UNIT = new L10n()
            .put("en", "Can buy this unit")
            .put("ru", "Можно купить этот юнит");

    public static final L10n CANT_BUY_THIS_UNIT = new L10n()
            .put("en", "Can't buy this unit")
            .put("ru", "Невозможно купить этот юнит");

    public static final L10n CANT_BUILD_UNIT_OTHER_ACTION_IN_PROGRESS = new L10n()
            .put("en", "Can't start unit's building as there is other action is in progress.")
            .put("ru", "Нельзя начать строительство юнита, пока идет другое строительство.");

    public static final L10n CAN_START_CONSTRUCTION = new L10n()
            .put("en", "Can start construction")
            .put("ru", "Можно начать строительство");

    public static final L10n INVALID_BUILDING = new L10n()
            .put("en", "Invalid building")
            .put("ru", "Неверное здание");

    public static final L10n INVALID_UNIT = new L10n()
            .put("en", "Invalid unit")
            .put("ru", "Неверный юнит");

    public static final L10n BUILDING_CONSTRUCTION_IS_STARTED = new L10n()
            .put("en", "Building construction is started")
            .put("ru", "Начато строительство здания");

    public static final L10n UNIT_CONSTRUCTION_IS_STARTED = new L10n()
            .put("en", "Unit's construction is started")
            .put("ru", "Начато строительство юнита");

    public static final L10n NAME = new L10n()
            .put("en", "Name")
            .put("ru", "Название");

    public static final L10n BUILDING_NOT_FOUND = new L10n()
            .put("en", "Building not found")
            .put("ru", "Здание не найдено");

    public static final L10n CITY_NOT_FOUND = new L10n()
            .put("en", "City not found")
            .put("ru", "Город не найден");

    public static final L10n CITY_CANT_PLACE_UNIT = new L10n()
            .put("en", "City can't place the unit")
            .put("ru", "Город не может принять этот юнит");

    /** Events */

    public static final L10n NEW_CITY_EVENT = new L10n()
            .put("en", "A new city %s was founded")
            .put("ru", "Основан новый %s город");

    public static final L10n REMOVE_CITY_EVENT = new L10n()
        .put("en", "City %s was removed")
        .put("ru", "Город %s удален");

    public static final L10n CITY_WAS_CAPTURED = new L10n()
            .put("en", "City %s was captured")
            .put("ru", "Город %s был захвачен");

    public static final L10n UNIT_HAS_CAPTURED_CITY = new L10n()
            .put("en", "A unit %s has captured the city")
            .put("ru", "Юнит %s захватил город");

    public static final L10n NEW_BUILDING_BUILT_EVENT = new L10n()
            .put("en", "A new building %s has been constructed")
            .put("ru", "Было построено %s новое здание");

    public static final L10n NEW_UNIT_BUILT_EVENT = new L10n()
            .put("en", "A new unit %s has been constructed")
            .put("ru", "Был построен %s новый юнит");

    /** Population */

    public static final L10n STARVATION_STARTED = new L10n()
            .put("en", "All food is consumed, the starvation has started")
            .put("ru", "Пища закончилась, начался голод");

    public static final L10n STARVATION_ENDED = new L10n()
            .put("en", "The starvation has ended")
            .put("ru", "Голод закончился");

    public static final L10n CITIZEN_WAS_BORN = new L10n()
            .put("en", "A citizen was born in city %s")
            .put("ru", "Родился житель in city %s");

    public static final L10n CITIZEN_HAS_DIED = new L10n()
            .put("en", "A citizen has died in city %s")
            .put("ru", "Умер житель in city %s");

    /** Statistic */

    public static final L10n POPULATION = new L10n()
            .put("en", "Population")
            .put("ru", "Население");

    public static final L10n DEFENSE_STRENGTH = new L10n()
            .put("en", "Defense Strength")
            .put("ru", "Мощь обороны");

    public static final L10n PRODUCTION = new L10n()
            .put("en", "Production")
            .put("ru", "Производство");

    public static final L10n GOLD = new L10n()
            .put("en", "Gold")
            .put("ru", "Золото");

    public static final L10n FOOD = new L10n()
            .put("en", "Food")
            .put("ru", "Пища");

    public static final L10n HAPPINESS = new L10n()
            .put("en", "Happiness")
            .put("ru", "Настроение");

    /** Economics */

    public static final L10n CITIZEN_FOOD_EXPENSES = new L10n()
            .put("en", "Food consumed by citizen")
            .put("ru", "Пища, потребленная жителями");

    public static final L10n FOUNDED_SETTLERS = new L10n()
            .put("en", "Settlers founded a city %s")
            .put("ru", "Поселенцы, которые основали город %s");

    public static final L10n ACCUMULATION_SUPPLY = new L10n()
            .put("en", "Accumulation Supply")
            .put("ru", "Накопление");

    /** Cities */

    public static final L10n MOSCOW = new L10n()
            .put("en", "Moscow")
            .put("ru", "Москва");

    public static final L10n SAINT_PETERSBURG = new L10n()
            .put("en", "Saint Petersburg")
            .put("ru", "Санкт-Петерсбург");

    public static final L10n WASHINGTON = new L10n()
            .put("en", "Washington")
            .put("ru", "Вашингтон");

    public static final L10n TOKYO = new L10n()
        .put("en", "Tokyo")
        .put("ru", "Токио");

    public static L10nMap<L10n, L10nList> CITIES = new L10nMap<L10n, L10nList>()
            .put(L10nCivilization.RUSSIA, new L10nList()
                .add(L10nCity.MOSCOW)
                .add(L10nCity.SAINT_PETERSBURG)
            )
            .put(L10nCivilization.AMERICA, new L10nList()
                .add(L10nCity.WASHINGTON)
            )
            .put(L10nCivilization.JAPAN, new L10nList()
                .add(L10nCity.TOKYO)
            );
}
