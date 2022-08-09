package com.tsoft.civilization.unit.civil.workers;

import com.tsoft.civilization.util.l10n.L10n;

public class L10nWorkers {

    public static final L10n REMOVE_FOREST_NAME = new L10n()
            .put("en", "Remove Forest")
            .put("ru", "Удалить лес");

    public static final L10n REMOVE_FOREST_DESCRIPTION = new L10n()
            .put("en", "The forest will be removed from the tile")
            .put("ru", "Лес будет удален с этой ячейки");

    public static final L10n REMOVE_HILL_NAME = new L10n()
            .put("en", "Remove Hill")
            .put("ru", "Удалить холм");

    public static final L10n REMOVE_HILL_DESCRIPTION = new L10n()
            .put("en", "The hill will be removed. The Mining technology must be open")
            .put("ru", "Холм будет удален. Должна быть исследована технология добычи");

    /** Actions */
    public static final L10n CAN_BUILD_IMPROVEMENT = new L10n()
        .put("en", "Improvement %s can be built")
        .put("ru", "Улучшение %s может быть построено");

    public static final L10n BUILDING_IMPROVEMENT = new L10n()
        .put("en", "Building improvement %s")
        .put("ru", "Строится улучшение %s");

    public static final L10n IMPROVEMENT_IS_BUILT = new L10n()
        .put("en", "Improvement %s is built")
        .put("ru", "Улучшение %s построено");

    public static final L10n CAN_REMOVE_FOREST = new L10n()
            .put("en", "Forest can be removed")
            .put("ru", "Лес может быть вырублен");

    public static final L10n CAN_REMOVE_HILL = new L10n()
            .put("en", "Hill can be removed")
            .put("ru", "Холм может быть удален");

    public static final L10n FOREST_IS_REMOVED = new L10n()
            .put("en", "Forest is removed")
            .put("ru", "Лес вырублен");

    public static final L10n HILL_IS_REMOVED = new L10n()
            .put("en", "Hill is removed")
            .put("ru", "Холм удален");

    public static final L10n REMOVING_FOREST = new L10n()
            .put("en", "Workers are removing the forest")
            .put("ru", "Рабочие вырубают лес");

    public static final L10n REMOVING_HILL = new L10n()
            .put("en", "Workers are removing the hill")
            .put("ru", "Рабочие удаляют холм");

    public static final L10n FAIL_NO_MINING_TECHNOLOGY = new L10n()
            .put("en", "Mining must be opened")
            .put("ru", "Должна быть открыта Добыча руды");

    public static final L10n FAIL_NO_FOREST_HERE = new L10n()
            .put("en", "There is no forest to remove")
            .put("ru", "Нет леса для удаления");

    public static final L10n FAIL_NO_HILL_HERE = new L10n()
            .put("en", "There is no hill to remove")
            .put("ru", "Здесь нету холма для удаления");

    public static final L10n FAIL_FOREST_MUST_BE_REMOVED_FIRST = new L10n()
            .put("en", "Forest must be removed first")
            .put("ru", "Сначала должен быть вырублен лес");
}
