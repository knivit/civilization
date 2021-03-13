package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.L10n.L10nList;

public class L10nCivilization {
    public static final L10n CIVILIZATION_NAME = new L10n()
            .put("en", "Civilization")
            .put("ru", "Цивилизация");

    /** Web */

    public static final L10n FEATURES = new L10n()
            .put("en", "Features")
            .put("ru", "Характеристики");

    public static final L10n AVAILABLE_UNITS = new L10n()
            .put("en", "Available Units")
            .put("ru", "Доступные юниты");

    public static final L10n AVAILABLE_CITIES = new L10n()
            .put("en", "Available Cities")
            .put("ru", "Доступные города");

    public static final L10n CITIES_COUNT = new L10n()
            .put("en", "Number of cities")
            .put("ru", "Количество городов");

    /** Actions */

    public static final L10n AVAILABLE_ACTIONS = new L10n()
            .put("en", "Available Actions")
            .put("ru", "Доступны действия");

    public static final L10n NEXT_TURN = new L10n()
            .put("en", "Next Turn")
            .put("ru", "Следующий ход");

    public static final L10n NEXT_MOVE_DESCRIPTION = new L10n()
            .put("en", "Allow other civilizations take their actions")
            .put("ru", "Позволяет другим цивилизациям выполнить действия");

    public static final L10n MOVED = new L10n()
            .put("en", "Moved")
            .put("ru", "Ход выполнен");

    public static final L10n CAN_NEXT_TURN = new L10n()
            .put("en", "Can go next")
            .put("ru", "Можно идти дальше");

    public static final L10n AWAITING_OTHERS_TO_MOVE = new L10n()
            .put("en", "Awaiting other civilizations to move")
            .put("ru", "Ожидание хода других цивилизаций");

    public static final L10n CAN_DECLARE_WAR = new L10n()
            .put("en", "Can declare a war")
            .put("ru", "Можно объявить войну");

    public static final L10n DECLARE_WAR_NAME = new L10n()
            .put("en", "War")
            .put("ru", "Война");

    public static final L10n CONFIRM_DECLARE_WAR = new L10n()
            .put("en", "Do you want to declare a war ?")
            .put("ru", "Вы хотите объявить войну ?");

    public static final L10n ALREADY_WAR = new L10n()
            .put("en", "There is already a war")
            .put("ru", "Война уже объявлена");

    public static final L10n DECLARE_WAR_DESCRIPTION = new L10n()
            .put("en", "Declare a war to this civilization")
            .put("ru", "Объявить войну этой цивилизации");

    public static final L10n WRONG_CIVILIZATION = new L10n()
            .put("en", "Wrong civilization")
            .put("ru", "Неверная цивилизация");

    public static final L10n WRONG_ERA_OR_TECHNOLOGY = new L10n()
            .put("en", "Wrong Era or insufficient civilization's technology level")
            .put("ru", "Неверная эра или уровень развития цивилизации недостаточен");

    public static final L10n NOT_ENOUGH_MONEY = new L10n()
            .put("en", "Not enough money")
            .put("ru", "Недостаточно денег");

    /** Statistic */

    public static final L10n POPULATION = new L10n()
            .put("en", "Population")
            .put("ru", "Население");

    public static final L10n MILITARY_UNITS_COUNT = new L10n()
            .put("en", "Military Units")
            .put("ru", "Военные юниты");

    public static final L10n CIVIL_UNITS_COUNT = new L10n()
            .put("en", "Civil Units")
            .put("ru", "Количество гражданских юнитов");

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

    /** Economic */

    public static final L10n UNIT_KEEPING_EXPENSES = new L10n()
            .put("en", "Units keeping expenses")
            .put("ru", "Затраты на содержание юнитов");

    public static final L10n BUY_UNIT_EVENT = new L10n()
            .put("en", "A unit was bought")
            .put("ru", "Юнит был куплен");

    public static final L10n BUY_BUILDING_EVENT = new L10n()
            .put("en", "A building %s was bought")
            .put("ru", "Куплено здание %s");

    public static final L10n ACCUMULATION_SUPPLY = new L10n()
            .put("en", "Accumulated supply")
            .put("ru", "Накопления");

    public static final L10n GIFT_RECEIVED = new L10n()
        .put("en", "A gift %s received from %s")
        .put("ru", "Получен подарок %s от %s");

    /** Civilizations */

    public static final L10n RUSSIA = new L10n()
            .put("en", "Russia")
            .put("ru", "Россия");

    public static final L10n AMERICA = new L10n()
            .put("en", "America")
            .put("ru", "Америка");

    public static final L10n JAPAN = new L10n()
            .put("en", "Japan")
            .put("ru", "Япония");

    public static final L10nList CIVILIZATIONS = new L10nList()
            .add(L10nCivilization.RUSSIA)
            .add(L10nCivilization.AMERICA)
            .add(L10nCivilization.JAPAN);
}
