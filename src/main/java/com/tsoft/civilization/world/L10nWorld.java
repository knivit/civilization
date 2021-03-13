package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10n;

public class L10nWorld {
    public static final L10n INVALID_LOCATION = new L10n()
            .put("en", "Invalid location")
            .put("ru", "Неверные координаты");

    public static final L10n WORLD_CREATED = new L10n()
        .put("en", "World created")
        .put("ru", "Мир создан");

    public static final L10n WORLD_CANT_BE_CREATED = new L10n()
        .put("en", "World can not be created")
        .put("ru", "Мир не может быть создан");

    public static final L10n INVALID_MAP_SIZE = new L10n()
        .put("en", "Map size must be within [2..1000][2..1000]")
        .put("ru", "Размер карты должен быть в пределах [2..1000][2..1000]");

    public static final L10n INVALID_WORLD_NAME = new L10n()
        .put("en", "Invalid world's name")
        .put("ru", "Неверное название мира");

    public static final L10n INVALID_CIVILIZATIONS_NUMBER = new L10n()
        .put("en", "Number of civilizations must be within [1..16]")
        .put("ru", "Количество цивилизаций должно быть в пределах [1..16]");

    public static final L10n CIVILIZATION_JOINED = new L10n()
        .put("en", "A civilization joined to the world")
        .put("ru", "Цивилизация присоединилась к миру");

    /** Relations */

    public static final L10n RELATIONS_NAME = new L10n()
            .put("en", "Relations")
            .put("ru", "Отношения");

    public static final L10n WAR_RELATIONS_DESCRIPTION = new L10n()
            .put("en", "War")
            .put("ru", "Война");

    public static final L10n BAD_RELATIONS_DESCRIPTION = new L10n()
            .put("en", "Bad")
            .put("ru", "Плохие");

    public static final L10n NEUTRAL_RELATIONS_DESCRIPTION = new L10n()
            .put("en", "Neutral")
            .put("ru", "Нейтральные");

    public static final L10n GOOD_RELATIONS_DESCRIPTION = new L10n()
            .put("en", "Good")
            .put("ru", "Хорошие");

    public static final L10n FRIENDS_RELATIONS_DESCRIPTION = new L10n()
            .put("en", "Friends")
            .put("ru", "Друзья");

    /** Era */

    public static final L10n ANCIENT_ERA = new L10n()
            .put("en", "Ancient")
            .put("ru", "Древняя");

    public static final L10n CLASSICAL_ERA = new L10n()
            .put("en", "Classical")
            .put("ru", "Классическая");

    public static final L10n MEDIEVAL_ERA = new L10n()
            .put("en", "Medieval")
            .put("ru", "Средневековая");

    public static final L10n RENAISSANCE_ERA = new L10n()
            .put("en", "Renaissance")
            .put("ru", "Ренессанс");

    public static final L10n INDUSTRIAL_ERA = new L10n()
            .put("en", "Industrial")
            .put("ru", "Промышленная");

    public static final L10n MODERN_ERA = new L10n()
            .put("en", "Modern")
            .put("ru", "Современная");

    public static final L10n ATOMIC_ERA = new L10n()
            .put("en", "Atomic")
            .put("ru", "Атомная");

    public static final L10n INFORMATION_ERA = new L10n()
            .put("en", "Information")
            .put("ru", "Информационная");

    /** Web */

    public static final L10n JOIN_WORLD_BUTTON = new L10n()
            .put("en", "Join to Play")
            .put("ru", "Зайти и играть");

    public static final L10n SPECTATOR_WORLD_BUTTON = new L10n()
            .put("en", "Join as a Spectator")
            .put("ru", "Зайти и смотреть");

    public static final L10n CREATE_NEW_WORLD_BUTTON = new L10n()
            .put("en", "Create New World")
            .put("ru", "Создать новый мир");

    public static final L10n INPUT_WORLD_NAME = new L10n()
            .put("en", "World Name")
            .put("ru", "Название мира");

    public static final L10n INPUT_WORLD_TYPE = new L10n()
            .put("en", "World Type")
            .put("ru", "Тип мира");

    public static final L10n INPUT_MAP_WIDTH = new L10n()
            .put("en", "Map Width")
            .put("ru", "Ширина карты");

    public static final L10n INPUT_MAX_NUMBER_OF_CIVILIZATIONS = new L10n()
            .put("en", "Max number of Civilizations")
            .put("ru", "Макс. кол-во цивилизаций");

    public static final L10n INPUT_MAP_HEIGHT = new L10n()
            .put("en", "Map Height")
            .put("ru", "Высота карты");

    public static final L10n INPUT_CLIMATE = new L10n()
            .put("en", "Climate")
            .put("ru", "Климат");

    public static final L10n INPUT_LANGUAGE = new L10n()
            .put("en", "Language")
            .put("ru", "Язык");

    public static final L10n BACK_BUTTON = new L10n()
            .put("en", "Back to the World List")
            .put("ru", "Назад к списку миров");

    /** Events */

    public static final L10n DECLARE_WAR_EVENT = new L10n()
            .put("en", "A war has been declared between %s and %s !")
            .put("ru", "Была объявлена война between %s and %s !");

    public static final L10n DECLARE_FRIENDS_EVENT = new L10n()
            .put("en", "A war has stopped between %s and %s !")
            .put("ru", "Война прекращена between %s and %s !");

    public static final L10n NEW_CIVILIZATION_EVENT = new L10n()
            .put("en", "A new Civilization %s has been founded on %s")
            .put("ru", "Была основана %s новая цивилизация %s");

    public static final L10n DESTROY_CIVILIZATION_EVENT = new L10n()
        .put("en", "Civilization %s has been destroyed")
        .put("ru", "Цивилизация %s уничтожена");

    public static final L10n MOVE_START_EVENT = new L10n()
        .put("en", "Civilization %s move begins")
        .put("ru", "Цивилизация %s начинает свой ход");

    public static final L10n MOVE_DONE_EVENT = new L10n()
            .put("en", "Civilization %s has made its move")
            .put("ru", "Цивилизация %s сделала ход");

    public static final L10n NEW_YEAR_START_EVENT = new L10n()
        .put("en", "Started a new Year %d !")
        .put("ru", "Начался новый год %d !");

    public static final L10n NEW_YEAR_COMPLETE_EVENT = new L10n()
            .put("en", "All civilizations has moved into new Year %d")
            .put("ru", "Все цивилизации вошли в Новый Год %d");
}
