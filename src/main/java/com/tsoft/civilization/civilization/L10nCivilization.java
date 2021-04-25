package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.L10n.L10nList;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.RU;

public class L10nCivilization {

    public static final L10n CIVILIZATION_NAME = new L10n()
        .put(EN, "Civilization")
        .put(RU, "Цивилизация");

    /** Web */

    public static final L10n FEATURES = new L10n()
        .put(EN, "Features")
        .put(RU, "Характеристики");

    public static final L10n AVAILABLE_UNITS = new L10n()
        .put(EN, "Available Units")
        .put(RU, "Доступные юниты");

    public static final L10n AVAILABLE_CITIES = new L10n()
        .put(EN, "Available Cities")
        .put(RU, "Доступные города");

    public static final L10n CITIES_COUNT = new L10n()
        .put(EN, "Number of cities")
        .put(RU, "Количество городов");

    /** Actions */

    public static final L10n AVAILABLE_ACTIONS = new L10n()
        .put(EN, "Available actions")
        .put(RU, "Доступны действия");

    public static final L10n NEXT_TURN = new L10n()
        .put(EN, "Next Turn")
        .put(RU, "Следующий ход");

    public static final L10n NEXT_MOVE_DESCRIPTION = new L10n()
        .put(EN, "Allow other civilizations take their actions")
        .put(RU, "Позволяет другим цивилизациям выполнить действия");

    public static final L10n MOVE_IN_PROGRESS = new L10n()
        .put(EN, "Move in progress")
        .put(RU, "Выполнение хода");

    public static final L10n MOVE_DONE = new L10n()
        .put(EN, "Move done")
        .put(RU, "Ход выполнен");

    public static final L10n NO_ACTIONS_AVAILABLE = new L10n()
        .put(EN, "Not actions available")
        .put(RU, "Нет доступных действий");

    public static final L10n CAN_DECLARE_WAR = new L10n()
        .put(EN, "Can declare a war")
        .put(RU, "Можно объявить войну");

    public static final L10n DECLARE_WAR_NAME = new L10n()
        .put(EN, "War")
        .put(RU, "Война");

    public static final L10n CONFIRM_DECLARE_WAR = new L10n()
        .put(EN, "Do you want to declare a war ?")
        .put(RU, "Вы хотите объявить войну ?");

    public static final L10n ALREADY_WAR = new L10n()
        .put(EN, "There is already a war")
        .put(RU, "Война уже объявлена");

    public static final L10n DECLARE_WAR_DESCRIPTION = new L10n()
        .put(EN, "Declare a war to this civilization")
        .put(RU, "Объявить войну этой цивилизации");

    public static final L10n WRONG_CIVILIZATION = new L10n()
        .put(EN, "Wrong civilization")
        .put(RU, "Неверная цивилизация");

    public static final L10n WRONG_ERA_OR_TECHNOLOGY = new L10n()
        .put(EN, "Wrong Era or insufficient civilization's technology level")
        .put(RU, "Неверная эра или уровень развития цивилизации недостаточен");

    public static final L10n NOT_ENOUGH_MONEY = new L10n()
        .put(EN, "Not enough money")
        .put(RU, "Недостаточно денег");

    /** Statistic */

    public static final L10n POPULATION = new L10n()
        .put(EN, "Population")
        .put(RU, "Население");

    public static final L10n MILITARY_UNITS_COUNT = new L10n()
        .put(EN, "Military Units")
        .put(RU, "Военные юниты");

    public static final L10n CIVIL_UNITS_COUNT = new L10n()
        .put(EN, "Civil Units")
        .put(RU, "Количество гражданских юнитов");

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

    /** Economic */

    public static final L10n UNIT_BOUGHT_EVENT = new L10n()
        .put(EN, "A unit $unitName was bought")
        .put(RU, "Юнит $unitName был куплен");

    public static final L10n BUILDING_BOUGHT_EVENT = new L10n()
        .put(EN, "A building $buildingName was bought")
        .put(RU, "Куплено здание $buildingName");

    public static final L10n GIFT_RECEIVED = new L10n()
        .put(EN, "A gift $receivedSupply received from $senderName")
        .put(RU, "Получен подарок $receivedSupply от $senderName");

    /** Civilizations */

    public static final L10n RANDOM = new L10n()
        .put(EN, "Random")
        .put(RU, "Случайная");

    public static final L10n BARBARIANS = new L10n()
        .put(EN, "Barbarians")
        .put(RU, "Варвары");

    public static final L10n RUSSIA = new L10n()
        .put(EN, "Russia")
        .put(RU, "Россия");

    public static final L10n AMERICA = new L10n()
        .put(EN, "America")
        .put(RU, "Америка");

    public static final L10n JAPAN = new L10n()
        .put(EN, "Japan")
        .put(RU, "Япония");

    // Tha barbarians don't include in this list - they are always AI
    public static final L10nList CIVILIZATIONS = new L10nList()
        .add(L10nCivilization.RUSSIA)
        .add(L10nCivilization.AMERICA)
        .add(L10nCivilization.JAPAN);
}
