package com.tsoft.civilization.web;

import com.tsoft.civilization.util.l10n.L10n;

public class L10nServer {
    public static final L10n WELCOME = new L10n()
        .put("en", "Welcome to the Pocket Civilization !")
        .put("ru", "Добро пожаловать в Карманную Цивилизацию !");

    public static final L10n SERVER_ERROR = new L10n()
        .put("en", "Server error")
        .put("ru", "Ошибка сервера");

    public static final L10n OBJECT_NOT_FOUND = new L10n()
        .put("en", "Object not found")
        .put("ru", "Объект не найден");

    public static final L10n INVALID_SESSION = new L10n()
        .put("en", "Invalid session")
        .put("ru", "Неверная сессия");

    public static final L10n INVALID_REQUEST = new L10n()
        .put("en", "Invalid request")
        .put("ru", "Неверный запрос к серверу");

    public static final L10n WORLD_NOT_FOUND = new L10n()
        .put("en", "World not found")
        .put("ru", "Карта не найдена");

    public static final L10n WORLD_IS_FULL = new L10n()
        .put("en", "World is full")
        .put("ru", "Мир заполнен");

    public static final L10n PLAYER_TYPE_MUST_BE_HUMAN_OR_BOT = new L10n()
        .put("en", "Player should be a human or a bot")
        .put("ru", "Игрок должен быть человеком или ботом");

    public static final L10n CANT_CREATE_CIVILIZATION = new L10n()
        .put("en", "Can not create a civilization")
        .put("ru", "Невозможно создать цивилизацию");

    public static final L10n NO_CIVILIZATION_AVAILABLE = new L10n()
        .put("en", "No civilization available")
        .put("ru", "Нет доступной цивилизации");

    public static final L10n CIVILIZATION_NOT_FOUND = new L10n()
        .put("en", "Civilization not found")
        .put("ru", "Цивилизация не найдена");

    public static final L10n CITY_NOT_FOUND = new L10n()
        .put("en", "City not found")
        .put("ru", "Город не найден");

    public static final L10n ACTION_NOT_FOUND = new L10n()
        .put("en", "Action not found")
        .put("ru", "Действие не найдено");
}
