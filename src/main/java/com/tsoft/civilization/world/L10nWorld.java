package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10n;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.RU;

public class L10nWorld {

    public static final L10n INVALID_LOCATION = new L10n()
        .put(EN, "Invalid location")
        .put(RU, "Неверные координаты");

    public static final L10n WORLD_CREATED = new L10n()
        .put(EN, "World created")
        .put(RU, "Мир создан");

    public static final L10n INVALID_MAP_SIZE = new L10n()
        .put(EN, "Map size must be within [2..1000][2..1000]")
        .put(RU, "Размер карты должен быть в пределах [2..1000][2..1000]");

    public static final L10n INVALID_WORLD_NAME = new L10n()
        .put(EN, "Invalid world's name")
        .put(RU, "Неверное название мира");

    public static final L10n CANT_CREATE_WORLD = new L10n()
        .put(EN, "Can not create a world")
        .put(RU, "Невозможно создать новый мир");

    public static final L10n CANT_CREATE_BARBARIANS = new L10n()
        .put(EN, "Can not create Barbarians")
        .put(RU, "Не удалось создать Варваров");

    public static final L10n INVALID_MAX_NUMBER_OF_CIVILIZATIONS = new L10n()
        .put(EN, "Number of civilizations must be within [1..16]")
        .put(RU, "Количество цивилизаций должно быть в пределах [1..16]");

    public static final L10n CIVILIZATION_JOINED = new L10n()
        .put(EN, "A civilization joined to the world")
        .put(RU, "Цивилизация присоединилась к миру");

    public static final L10n MOVE_STATE_HEADER = new L10n()
        .put(EN, "State")
        .put(RU, "Состояние");

    /** Relations */

    public static final L10n RELATIONS_NAME = new L10n()
        .put(EN, "Relations")
        .put(RU, "Отношения");

    public static final L10n WAR_RELATIONS_DESCRIPTION = new L10n()
        .put(EN, "War")
        .put(RU, "Война");

    public static final L10n BAD_RELATIONS_DESCRIPTION = new L10n()
        .put(EN, "Bad")
        .put(RU, "Плохие");

    public static final L10n NEUTRAL_RELATIONS_DESCRIPTION = new L10n()
        .put(EN, "Neutral")
        .put(RU, "Нейтральные");

    public static final L10n GOOD_RELATIONS_DESCRIPTION = new L10n()
        .put(EN, "Good")
        .put(RU, "Хорошие");

    public static final L10n FRIENDS_RELATIONS_DESCRIPTION = new L10n()
        .put(EN, "Friends")
        .put(RU, "Друзья");

    /** Era */

    public static final L10n ANCIENT_ERA = new L10n()
        .put(EN, "Ancient")
        .put(RU, "Древняя");

    public static final L10n CLASSICAL_ERA = new L10n()
        .put(EN, "Classical")
        .put(RU, "Классическая");

    public static final L10n MEDIEVAL_ERA = new L10n()
        .put(EN, "Medieval")
        .put(RU, "Средневековая");

    public static final L10n RENAISSANCE_ERA = new L10n()
        .put(EN, "Renaissance")
        .put(RU, "Ренессанс");

    public static final L10n INDUSTRIAL_ERA = new L10n()
        .put(EN, "Industrial")
        .put(RU, "Промышленная");

    public static final L10n MODERN_ERA = new L10n()
        .put(EN, "Modern")
        .put(RU, "Современная");

    public static final L10n ATOMIC_ERA = new L10n()
        .put(EN, "Atomic")
        .put(RU, "Атомная");

    public static final L10n INFORMATION_ERA = new L10n()
        .put(EN, "Information")
        .put(RU, "Информационная");

    /** Web */

    public static final L10n WORLD_HEADER = new L10n()
        .put(EN, "World")
        .put(RU, "Мир");

    public static final L10n ERA_HEADER = new L10n()
        .put(EN, "Era")
        .put(RU, "Эра");

    public static final L10n YEAR_HEADER = new L10n()
        .put(EN, "Year")
        .put(RU, "Год");

    public static final L10n CIVILIZATIONS_HEADER = new L10n()
        .put(EN, "Civilizations")
        .put(RU, "Цивилизации");

    public static final L10n SLOTS_AVAILABLE_HEADER = new L10n()
        .put(EN, "Slots Available")
        .put(RU, "Доступно мест");

    public static final L10n ACTION_HEADER = new L10n()
        .put(EN, "Action")
        .put(RU, "Действие");

    public static final L10n ADD_BOT_BUTTON = new L10n()
        .put(EN, "Add Bot")
        .put(RU, "Добавить бота");

    public static final L10n JOIN_WORLD_BUTTON = new L10n()
        .put(EN, "Join to Play")
        .put(RU, "Присоединиться к игре");

    public static final L10n SPECTATOR_WORLD_BUTTON = new L10n()
        .put(EN, "Join as a Spectator")
        .put(RU, "Зайти и смотреть");

    public static final L10n CREATE_NEW_WORLD_BUTTON = new L10n()
        .put(EN, "Create New World")
        .put(RU, "Создать новый мир");

    public static final L10n INPUT_WORLD_NAME = new L10n()
        .put(EN, "World Name")
        .put(RU, "Название мира");

    public static final L10n INPUT_WORLD_TYPE = new L10n()
        .put(EN, "World Type")
        .put(RU, "Тип мира");

    public static final L10n INPUT_MAP_WIDTH = new L10n()
        .put(EN, "Map Width")
        .put(RU, "Ширина карты");

    public static final L10n INPUT_MAX_NUMBER_OF_CIVILIZATIONS = new L10n()
        .put(EN, "Max number of Civilizations")
        .put(RU, "Макс. кол-во цивилизаций");

    public static final L10n INPUT_MAP_HEIGHT = new L10n()
        .put(EN, "Map Height")
        .put(RU, "Высота карты");

    public static final L10n INPUT_CLIMATE = new L10n()
        .put(EN, "Climate")
        .put(RU, "Климат");

    public static final L10n BACK_BUTTON = new L10n()
        .put(EN, "Back to the World List")
        .put(RU, "Назад к списку миров");

    /** Events */

    public static final L10n DECLARE_WAR_EVENT = new L10n()
        .put(EN, "A war has been declared between %s and %s !")
        .put(RU, "Была объявлена война between %s and %s !");

    public static final L10n DECLARE_FRIENDS_EVENT = new L10n()
        .put(EN, "A war has stopped between %s and %s !")
        .put(RU, "Война прекращена between %s and %s !");

    public static final L10n NEW_CIVILIZATION_EVENT = new L10n()
        .put(EN, "A new Civilization %s has been founded on %s")
        .put(RU, "Была основана %s новая цивилизация %s");

    public static final L10n DESTROY_CIVILIZATION_EVENT = new L10n()
        .put(EN, "Civilization %s has been destroyed")
        .put(RU, "Цивилизация %s уничтожена");

    public static final L10n MOVE_START_EVENT = new L10n()
        .put(EN, "Civilization %s move begins")
        .put(RU, "Цивилизация %s начинает свой ход");

    public static final L10n MOVE_DONE_EVENT = new L10n()
        .put(EN, "Civilization %s has made its move")
        .put(RU, "Цивилизация %s сделала ход");

    public static final L10n NEW_YEAR_START_EVENT = new L10n()
        .put(EN, "Started a new Year %d !")
        .put(RU, "Начался новый год %d !");

    public static final L10n NEW_YEAR_COMPLETE_EVENT = new L10n()
        .put(EN, "All civilizations has moved into new Year %d")
        .put(RU, "Все цивилизации вошли в Новый Год %d");
}
