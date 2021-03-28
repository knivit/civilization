package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.L10n.L10nList;
import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.civilization.L10nCivilization;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.RU;

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

    public static final L10n CITY_NAME = new L10n()
        .put(EN, "City")
        .put(RU, "Город");

    public static final L10n CITY_DESCRIPTION = new L10n()
        .put(EN, "Cities are source of power of your civilization")
        .put(RU, "Города - источник военной мощи вашей цивилизации");

    public static final L10n CANT_BUILD_BUILDING_OTHER_ACTION_IN_PROGRESS = new L10n()
        .put(EN, "Can't start a building as there is other action is in progress")
        .put(RU, "Нельзя начать строительство, потому что выполняется другое действие");

    public static final L10n CANT_BUILD_THIS_BUILDING = new L10n()
        .put(EN, "Can't build this building")
        .put(RU, "Невозможно построить это здание");

    public static final L10n CANT_BUILD_THIS_UNIT = new L10n()
        .put(EN, "Can't build this unit")
        .put(RU, "Невозможно построить этот юнит");

    public static final L10n CANT_BUY_THIS_BUILDING = new L10n()
        .put(EN, "Can't buy this building")
        .put(RU, "Невозможно купить это здание");

    public static final L10n CAN_BUY_THIS_UNIT = new L10n()
        .put(EN, "Can buy this unit")
        .put(RU, "Можно купить этот юнит");

    public static final L10n CANT_BUY_THIS_UNIT = new L10n()
        .put(EN, "Can't buy this unit")
        .put(RU, "Невозможно купить этот юнит");

    public static final L10n CANT_BUILD_UNIT_OTHER_ACTION_IN_PROGRESS = new L10n()
        .put(EN, "Can't start unit's building as there is other action is in progress.")
        .put(RU, "Нельзя начать строительство юнита, пока идет другое строительство.");

    public static final L10n CAN_START_CONSTRUCTION = new L10n()
        .put(EN, "Can start construction")
        .put(RU, "Можно начать строительство");

    public static final L10n INVALID_BUILDING = new L10n()
        .put(EN, "Invalid building")
        .put(RU, "Неверное здание");

    public static final L10n INVALID_UNIT = new L10n()
        .put(EN, "Invalid unit")
        .put(RU, "Неверный юнит");

    public static final L10n BUILDING_CONSTRUCTION_IS_STARTED = new L10n()
        .put(EN, "Building construction is started")
        .put(RU, "Начато строительство здания");

    public static final L10n UNIT_CONSTRUCTION_IS_STARTED = new L10n()
        .put(EN, "Unit's construction is started")
        .put(RU, "Начато строительство юнита");

    public static final L10n NAME = new L10n()
        .put(EN, "Name")
        .put(RU, "Название");

    public static final L10n BUILDING_NOT_FOUND = new L10n()
        .put(EN, "Building not found")
        .put(RU, "Здание не найдено");

    public static final L10n CITY_NOT_FOUND = new L10n()
        .put(EN, "City not found")
        .put(RU, "Город не найден");

    public static final L10n CITY_CANT_PLACE_UNIT = new L10n()
        .put(EN, "City can't place the unit")
        .put(RU, "Город не может принять этот юнит");

    /** Events */

    public static final L10n NEW_CITY_EVENT = new L10n()
        .put(EN, "A new city %s was founded")
        .put(RU, "Основан новый %s город");

    public static final L10n REMOVE_CITY_EVENT = new L10n()
        .put(EN, "City %s was removed")
        .put(RU, "Город %s удален");

    public static final L10n CITY_WAS_CAPTURED = new L10n()
        .put(EN, "City %s was captured")
        .put(RU, "Город %s был захвачен");

    public static final L10n UNIT_HAS_CAPTURED_CITY = new L10n()
        .put(EN, "A unit %s has captured the city")
        .put(RU, "Юнит %s захватил город");

    public static final L10n NEW_BUILDING_BUILT_EVENT = new L10n()
        .put(EN, "A new building %s has been constructed")
        .put(RU, "Было построено %s новое здание");

    public static final L10n NEW_UNIT_BUILT_EVENT = new L10n()
        .put(EN, "A new unit %s has been constructed")
        .put(RU, "Был построен %s новый юнит");

    /** Population */

    public static final L10n STARVATION_STARTED = new L10n()
        .put(EN, "All food is consumed, the starvation has started")
        .put(RU, "Пища закончилась, начался голод");

    public static final L10n STARVATION_ENDED = new L10n()
        .put(EN, "The starvation has ended")
        .put(RU, "Голод закончился");

    public static final L10n CITIZEN_WAS_BORN = new L10n()
        .put(EN, "A citizen was born in city %s")
        .put(RU, "Родился житель in city %s");

    public static final L10n CITIZEN_HAS_DIED = new L10n()
        .put(EN, "A citizen has died in city %s")
        .put(RU, "Умер житель in city %s");

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

    /** Economics */

    public static final L10n FOUNDED_SETTLERS = new L10n()
            .put(EN, "Settlers founded a city %s")
            .put(RU, "Поселенцы, которые основали город %s");

    /** Cities */

    public static final L10n MOSCOW = new L10n()
        .put(EN, "Moscow")
        .put(RU, "Москва");

    public static final L10n SAINT_PETERSBURG = new L10n()
        .put(EN, "Saint Petersburg")
        .put(RU, "Санкт-Петерсбург");

    public static final L10n WASHINGTON = new L10n()
        .put(EN, "Washington")
        .put(RU, "Вашингтон");

    public static final L10n TOKYO = new L10n()
        .put(EN, "Tokyo")
        .put(RU, "Токио");

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
