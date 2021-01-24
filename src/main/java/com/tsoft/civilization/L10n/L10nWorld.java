package com.tsoft.civilization.L10n;

public class L10nWorld {
    public static L10nMap INVALID_LOCATION = new L10nMap()
            .set("en", "Invalid location")
            .set("ru", "\u041d\u0435\u0432\u0435\u0440\u043d\u044b\u0435 \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b");

    public static L10nMap WORLD_CREATED = new L10nMap()
        .set("en", "World created")
        .set("ru", "Мир создан");

    public static L10nMap WORLD_CANT_BE_CREATED = new L10nMap()
        .set("en", "World can not be created")
        .set("ru", "Мир не может быть создан");

    public static L10nMap INVALID_MAP_SIZE = new L10nMap()
        .set("en", "Map size must be within [2..1000][2..1000]")
        .set("ru", "Размер карты должен быть в пределах [2..1000][2..1000]");

    public static L10nMap INVALID_WORLD_NAME = new L10nMap()
        .set("en", "Invalid world's name")
        .set("ru", "Неверное название мира");

    public static L10nMap INVALID_CIVILIZATIONS_NUMBER = new L10nMap()
        .set("en", "Number of civilizations must be within [1..16]")
        .set("ru", "Количество цивилизаций должно быть в пределах [1..16]");

    public static L10nMap CIVILIZATION_JOINED = new L10nMap()
        .set("en", "A civilization joined to the world")
        .set("ru", "Цивилизация присоединилась к миру");

    /** Relations */

    public static L10nMap RELATIONS_NAME = new L10nMap()
            .set("en", "Relations")
            .set("ru", "\u041e\u0442\u043d\u043e\u0448\u0435\u043d\u0438\u044f");

    public static L10nMap WAR_RELATIONS_DESCRIPTION = new L10nMap()
            .set("en", "War")
            .set("ru", "\u0412\u043e\u0439\u043d\u0430");

    public static L10nMap BAD_RELATIONS_DESCRIPTION = new L10nMap()
            .set("en", "Bad")
            .set("ru", "\u041f\u043b\u043e\u0445\u0438\u0435");

    public static L10nMap NEUTRAL_RELATIONS_DESCRIPTION = new L10nMap()
            .set("en", "Neutral")
            .set("ru", "\u041d\u0435\u0439\u0442\u0440\u0430\u043b\u044c\u043d\u044b\u0435");

    public static L10nMap GOOD_RELATIONS_DESCRIPTION = new L10nMap()
            .set("en", "Good")
            .set("ru", "\u0425\u043e\u0440\u043e\u0448\u0438\u0435");

    public static L10nMap FRIENDS_RELATIONS_DESCRIPTION = new L10nMap()
            .set("en", "Friends")
            .set("ru", "\u0414\u0440\u0443\u0437\u044c\u044f");

    /** Era */

    public static L10nMap ANCIENT_ERA = new L10nMap()
            .set("en", "Ancient")
            .set("ru", "\u0414\u0440\u0435\u0432\u043d\u044f\u044f");

    public static L10nMap CLASSICAL_ERA = new L10nMap()
            .set("en", "Classical")
            .set("ru", "\u041a\u043b\u0430\u0441\u0441\u0438\u0447\u0435\u0441\u043a\u0430\u044f");

    public static L10nMap MEDIEVAL_ERA = new L10nMap()
            .set("en", "Medieval")
            .set("ru", "\u0421\u0440\u0435\u0434\u043d\u0435\u0432\u0435\u043a\u043e\u0432\u0430\u044f");

    public static L10nMap RENAISSANCE_ERA = new L10nMap()
            .set("en", "Renaissance")
            .set("ru", "\u0420\u0435\u043d\u0435\u0441\u0441\u0430\u043d\u0441");

    public static L10nMap INDUSTRIAL_ERA = new L10nMap()
            .set("en", "Industrial")
            .set("ru", "\u041f\u0440\u043e\u043c\u044b\u0448\u043b\u0435\u043d\u043d\u0430\u044f");

    public static L10nMap MODERN_ERA = new L10nMap()
            .set("en", "Modern")
            .set("ru", "\u0421\u043e\u0432\u0440\u0435\u043c\u0435\u043d\u043d\u0430\u044f");

    public static L10nMap ATOMIC_ERA = new L10nMap()
            .set("en", "Atomic")
            .set("ru", "\u0410\u0442\u043e\u043c\u043d\u0430\u044f");

    public static L10nMap INFORMATION_ERA = new L10nMap()
            .set("en", "Information")
            .set("ru", "\u0418\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u043e\u043d\u043d\u0430\u044f");

    /** Web */

    public static L10nMap JOIN_WORLD_BUTTON = new L10nMap()
            .set("en", "Join to Play")
            .set("ru", "\u0417\u0430\u0439\u0442\u0438 \u0438 \u0438\u0433\u0440\u0430\u0442\u044c");

    public static L10nMap SPECTATOR_WORLD_BUTTON = new L10nMap()
            .set("en", "Join as a Spectator")
            .set("ru", "\u0417\u0430\u0439\u0442\u0438 \u0438 \u0441\u043c\u043e\u0442\u0440\u0435\u0442\u044c");

    public static L10nMap CREATE_NEW_WORLD_BUTTON = new L10nMap()
            .set("en", "Create New World")
            .set("ru", "\u0421\u043e\u0437\u0434\u0430\u0442\u044c \u043d\u043e\u0432\u044b\u0439 \u043c\u0438\u0440");

    public static L10nMap INPUT_WORLD_NAME = new L10nMap()
            .set("en", "World Name")
            .set("ru", "\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043c\u0438\u0440\u0430");

    public static L10nMap INPUT_WORLD_TYPE = new L10nMap()
            .set("en", "World Type")
            .set("ru", "\u0422\u0438\u043f \u043c\u0438\u0440\u0430");

    public static L10nMap INPUT_MAP_WIDTH = new L10nMap()
            .set("en", "Map Width")
            .set("ru", "\u0428\u0438\u0440\u0438\u043d\u0430 \u043a\u0430\u0440\u0442\u044b");

    public static L10nMap INPUT_MAX_NUMBER_OF_CIVILIZATIONS = new L10nMap()
            .set("en", "Max number of Civilizations")
            .set("ru", "\u041c\u0430\u043a\u0441. \u043a\u043e\u043b-\u0432\u043e \u0446\u0438\u0432\u0438\u043b\u0438\u0437\u0430\u0446\u0438\u0439");

    public static L10nMap INPUT_MAP_HEIGHT = new L10nMap()
            .set("en", "Map Height")
            .set("ru", "\u0412\u044b\u0441\u043e\u0442\u0430 \u043a\u0430\u0440\u0442\u044b");

    public static L10nMap INPUT_CLIMATE = new L10nMap()
            .set("en", "Climate")
            .set("ru", "\u041a\u043b\u0438\u043c\u0430\u0442");

    public static L10nMap INPUT_LANGUAGE = new L10nMap()
            .set("en", "Language")
            .set("ru", "\u042f\u0437\u044b\u043a");

    public static L10nMap BACK_BUTTON = new L10nMap()
            .set("en", "Back to the World List")
            .set("ru", "\u041d\u0430\u0437\u0430\u0434 \u043a \u0441\u043f\u0438\u0441\u043a\u0443 \u043c\u0438\u0440\u043e\u0432");

    /** Events */

    public static L10nMap DECLARE_WAR_EVENT = new L10nMap()
            .set("en", "A war has been declared between %s and %s !")
            .set("ru", "\u0411\u044b\u043b\u0430 \u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0430 \u0432\u043e\u0439\u043d\u0430 between %s and %s !");

    public static L10nMap DECLARE_FRIENDS_EVENT = new L10nMap()
            .set("en", "A war has stopped between %s and %s !")
            .set("ru", "\u0412\u043e\u0439\u043d\u0430 \u043f\u0440\u0435\u043a\u0440\u0430\u0449\u0435\u043d\u0430 between %s and %s !");

    public static L10nMap NEW_CIVILIZATION_EVENT = new L10nMap()
            .set("en", "A new Civilization %s has been founded on %s")
            .set("ru", "\u0411\u044b\u043b\u0430 \u043e\u0441\u043d\u043e\u0432\u0430\u043d\u0430 %s \u043d\u043e\u0432\u0430\u044f \u0446\u0438\u0432\u0438\u043b\u0438\u0437\u0430\u0446\u0438\u044f %s");

    public static L10nMap DESTROY_CIVILIZATION_EVENT = new L10nMap()
        .set("en", "Civilization %s has been destroyed")
        .set("ru", "Цивилизация %s уничтожена");

    public static L10nMap MOVE_START_EVENT = new L10nMap()
        .set("en", "Civilization %s move begins")
        .set("ru", "Цивилизация %s начинает свой ход");

    public static L10nMap MOVE_DONE_EVENT = new L10nMap()
            .set("en", "Civilization %s has made its move")
            .set("ru", "\u0426\u0438\u0432\u0438\u043b\u0438\u0437\u0430\u0446\u0438\u044f %s \u0441\u0434\u0435\u043b\u0430\u043b\u0430 \u0445\u043e\u0434");

    public static L10nMap NEW_YEAR_START_EVENT = new L10nMap()
        .set("en", "Started a new Year %d !")
        .set("ru", "Начался новый год %d !");

    public static L10nMap NEW_YEAR_COMPLETE_EVENT = new L10nMap()
            .set("en", "All civilizations has moved into new Year %d")
            .set("ru", "\u0412\u0441\u0435 \u0446\u0438\u0432\u0438\u043b\u0438\u0437\u0430\u0446\u0438\u0438 \u0432\u043e\u0448\u043b\u0438 \u0432 \u041d\u043e\u0432\u044b\u0439 \u0413\u043e\u0434 %d");
}
