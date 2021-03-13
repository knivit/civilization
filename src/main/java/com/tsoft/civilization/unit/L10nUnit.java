package com.tsoft.civilization.unit;

import com.tsoft.civilization.L10n.L10n;

public class L10nUnit {
    public static final L10n NO_UNITS = new L10n()
            .put("en", "No Units")
            .put("ru", "Юнитов нет");

    public static final L10n UNITS_ON_TILE = new L10n()
            .put("en", "Units on the Tile")
            .put("ru", "Юниты на этой ячейке");

    public static final L10n NAME = new L10n()
            .put("en", "Name")
            .put("ru", "Имя");

    public static final L10n UNIT_NOT_FOUND = new L10n()
            .put("en", "Unit not found")
            .put("ru", "Юнит не найден");

    public static final L10n COMBAT_STRENGTH = new L10n()
            .put("en", "Combat Strength")
            .put("ru", "Сила в нападении");

    public static final L10n MOVE_NAME = new L10n()
            .put("en", "Move")
            .put("ru", "Идти");

    public static final L10n MOVE_DESCRIPTION = new L10n()
            .put("en", "Move unit to a tile")
            .put("ru", "Переместить юнит в другую ячейку");

    public static final L10n ATTACK_NAME = new L10n()
            .put("en", "Attack")
            .put("ru", "Атака");

    public static final L10n ATTACK_DESCRIPTION = new L10n()
            .put("en", "Attack foreign unit or a city")
            .put("ru", "Атаковать иностранный юнит или город");

    public static final L10n CAPTURE_NAME = new L10n()
            .put("en", "Capture")
            .put("ru", "Захватить");

    public static final L10n CAPTURE_DESCRIPTION = new L10n()
            .put("en", "Capture foreign civil unit")
            .put("ru", "Захватить иностранный гражданский юнит");

    public static final L10n DESTROY_UNIT_NAME = new L10n()
            .put("en", "Destroy Unit")
            .put("ru", "Разрашить юнит");

    public static final L10n DESTROY_UNIT_DESCRIPTION = new L10n()
            .put("en", "Destroy unit and save your money on unit's expenses")
            .put("ru", "Уничтожить юнит и сохранить деньги от расходов на содержание юнита");

    /** Events */

    public static final L10n UNIT_WAS_DESTROYED_EVENT = new L10n()
            .put("en", "Unit %s was destroyed")
            .put("ru", "Юнит %s был уничтожен");

    public static final L10n UNIT_HAS_WON_ATTACK_EVENT = new L10n()
            .put("en", "Unit has won the attack")
            .put("ru", "Юнит выиграл атаку");

    public static final L10n UNIT_MOVED_EVENT = new L10n()
            .put("en", "Unit has moved")
            .put("ru", "Юнит переместился");

    public static final L10n ATTACK_DONE_EVENT = new L10n()
            .put("en", "Attack done")
            .put("ru", "Атака выполнена");

    /** Actions */

    public static final L10n UNIT_WAS_DESTROYED = new L10n()
            .put("en", "Unit was destroyed")
            .put("ru", "Юнит был уничтожен");

    public static final L10n INVALID_LOCATION = new L10n()
            .put("en", "Invalid location")
            .put("ru", "Неверное местоположение");

    public static final L10n NO_LOCATIONS_TO_MOVE = new L10n()
            .put("en", "There are no locations to move in")
            .put("ru", "Нет возможных ячеек для перемещения");

    public static final L10n NO_TARGETS_TO_ATTACK = new L10n()
            .put("en", "There are no targets to attack")
            .put("ru", "Нет целей для атаки");

    public static final L10n MELEE_NOT_ENOUGH_PASS_SCORE = new L10n()
            .put("en", "Can't move on target's tile")
            .put("ru", "Невозможно перейти на ячейку цели");

    public static final L10n UNIT_MOVED = new L10n()
            .put("en", "Unit moved")
            .put("ru", "Юнит перемещен");

    public static final L10n CAN_MOVE = new L10n()
            .put("en", "Unit can move there")
            .put("ru", "Юнит может быть перемещен");

    public static final L10n CAN_ATTACK = new L10n()
            .put("en", "Unit can attack")
            .put("ru", "Юнит может выполнить атаку");

    public static final L10n UNDERSHOOT = new L10n()
            .put("en", "Distance too long or missile can't overcome it")
            .put("ru", "Расстояние слишком большое или снаряд не может преодолеть его");

    public static final L10n TARGET_DESTROYED = new L10n()
            .put("en", "Target is destroyed")
            .put("ru", "Цель уничтожена");

    public static final L10n ATTACKED = new L10n()
            .put("en", "Attack is done")
            .put("ru", "Атака завершена");

    public static final L10n FOREIGN_UNIT_CAPTURED = new L10n()
            .put("en", "Foreign unit is captured")
            .put("ru", "Иностранный юнит захвачен");

    public static final L10n ATTACKER_IS_DESTROYED_DURING_ATTACK = new L10n()
            .put("en", "The attacker was destroyed during the attack")
            .put("ru", "Атакующий был уничтожен в ходе атаки");

    public static final L10n NO_LOCATIONS_TO_CAPTURE = new L10n()
            .put("en", "There is nothing to capture")
            .put("ru", "Для захвате ничего нет");

    public static final L10n CAN_CAPTURE = new L10n()
            .put("en", "There are units to capture")
            .put("ru", "Есть юниты для захвата");

    public static final L10n NOTHING_TO_CAPTURE = new L10n()
            .put("en", "There is nothing to capture at that location")
            .put("ru", "Нечего захватывать в данном месте");

    public static final L10n UNIT_DESTROYED = new L10n()
            .put("en", "The unit is destroyed successfully")
            .put("ru", "Юнит успешно уничтожен");

    public static final L10n CAN_DESTROY_UNIT = new L10n()
            .put("en", "The unit can be destroyed")
            .put("ru", "Юнит может быть удален");

    public static final L10n NOT_MILITARY_UNIT = new L10n()
            .put("en", "Not a military unit")
            .put("ru", "Не военный юнит");

    public static final L10n NO_PASS_SCORE = new L10n()
            .put("en", "Not enough the passing score")
            .put("ru", "Недостаточно ходов перемещения");

    public static final L10n LAST_SETTLERS_CANT_BE_DESTROYED = new L10n()
            .put("en", "The only settlers unit can not be destroyed")
            .put("ru", "The only settlers unit can not be destroyed");

    public static final L10n UNIT_WAS_BOUGHT = new L10n()
            .put("en", "An Unit was bought")
            .put("ru", "Юнит куплен");

    /** Web */

    public static final L10n FEATURES = new L10n()
            .put("en", "Features")
            .put("ru", "Характеристики");

    public static final L10n BUILD = new L10n()
            .put("en", "Build")
            .put("ru", "Построить");

    public static final L10n BUILD_DESCRIPTION = new L10n()
            .put("en", "Construct a unit")
            .put("ru", "Построить юнит");

    public static final L10n BUY = new L10n()
            .put("en", "Buy")
            .put("ru", "Купить");

    public static final L10n BUY_DESCRIPTION = new L10n()
            .put("en", "Buy a unit")
            .put("ru", "Купить юнит");

    public static final L10n STRENGTH = new L10n()
            .put("en", "Defense Strength")
            .put("ru", "Сила в защите");

    public static final L10n STRENGTH_HEADER = new L10n()
            .put("en", "DS")
            .put("ru", "СЗ");

    public static final L10n MELEE_ATTACK_STRENGTH = new L10n()
            .put("en", "Melee Attack Strength")
            .put("ru", "Сила атаки в ближнем бою");

    public static final L10n MELEE_ATTACK_STRENGTH_HEADER = new L10n()
            .put("en", "MAS")
            .put("ru", "САБ");

    public static final L10n CAN_CONQUER_CITY = new L10n()
            .put("en", "Can conquer a city ?")
            .put("ru", "Может захватывать города ?");

    public static final L10n ATTACK_EXPERIENCE = new L10n()
            .put("en", "Attack Experience")
            .put("ru", "Опыт атак");

    public static final L10n DEFENSE_EXPERIENCE = new L10n()
            .put("en", "Defense Experience")
            .put("ru", "Опыт защит");

    public static final L10n RANGED_ATTACK_STRENGTH = new L10n()
            .put("en", "Ranged Attack Strength")
            .put("ru", "Сила атаки в дальнем бою");

    public static final L10n RANGED_ATTACK_STRENGTH_HEADER = new L10n()
            .put("en", "RAS")
            .put("ru", "САД");

    public static final L10n RANGED_ATTACK_RADIUS = new L10n()
            .put("en", "Ranged Attack Radius")
            .put("ru", "Радиус атаки в дальнем бою");

    public static final L10n PASS_SCORE = new L10n()
            .put("en", "Pass Score")
            .put("ru", "Оставшиеся ходы");

    public static final L10n PASS_SCORE_HEADER = new L10n()
            .put("en", "P")
            .put("ru", "Х");

    /** Units List */

    public static final L10n ARCHERS_NAME = new L10n()
            .put("en", "Archers")
            .put("ru", "Лучники");

    public static final L10n ARCHERS_DESCRIPTION = new L10n()
            .put("en", "May not melee attack. The bombardment range for archers is 2 hexes.")
            .put("ru", "Не может атаковать в ближнем бою. Радиус обстрела - 2 ячейки.");

    public static final L10n GREAT_ARTIST_NAME = new L10n()
            .put("en", "Great Artist")
            .put("ru", "Великий художник");

    public static final L10n GREAT_ARTIST_DESCRIPTION = new L10n()
            .put("en", "Great Artist can paint a city which gives additional happiness")
            .put("ru", "Великий художник может украсить город и он станет приносить больше радости");

    public static final L10n GREAT_ENGINEER_NAME = new L10n()
            .put("en", "Great Engineer")
            .put("ru", "Великий инженер");

    public static final L10n GREAT_ENGINEER_DESCRIPTION = new L10n()
            .put("en", "Great Engineer can build an improvement which gives labour points")
            .put("ru", "Великий инженер способен построить усовершенствование, которое будет приносить дополнительный труд");

    public static final L10n GREAT_GENERAL_NAME = new L10n()
            .put("en", "Great General")
            .put("ru", "Великий генерал");

    public static final L10n GREAT_GENERAL_DESCRIPTION = new L10n()
            .put("en", "Great General can build a defense improvement on a tile or capture foreign tiles")
            .put("ru", "Великий генерал может построить оборонительное улучшение на клетке или захватить вражеские клетки");

    public static final L10n GREAT_MERCHANT_NAME = new L10n()
            .put("en", "Great Merchant")
            .put("ru", "Великий торговец");

    public static final L10n GREAT_MERCHANT_DESCRIPTION = new L10n()
            .put("en", "Great Merchant can improve relations with state-city")
            .put("ru", "Великий торговец поможет наладить отношения с городом-государством");

    public static final L10n GREAT_SCIENTIST_NAME = new L10n()
            .put("en", "Great Scientist")
            .put("ru", "Великий ученый");

    public static final L10n GREAT_SCIENTIST_DESCRIPTION = new L10n()
            .put("en", "Great Scientist can open a new technology")
            .put("ru", "Великий ученый может открыть новую технологию");

    public static final L10n SETTLERS_NAME = new L10n()
            .put("en", "Settlers")
            .put("ru", "Поселенцы");

    public static final L10n SETTLERS_DESCRIPTION = new L10n()
            .put("en", "Founds new cities to expand your empire. Growth of the City is stopped while this unit is being built.")
            .put("ru", "Расширяет вашу империю, основывая новые города. Рост города остановлен, пока этот юнит строится.");

    public static final L10n WARRIORS_NAME = new L10n()
            .put("en", "Warriors")
            .put("ru", "Воины");

    public static final L10n WARRIORS_DESCRIPTION = new L10n()
            .put("en", "The Warrior has a fairly decent combat strength, and can be useful in the Ancient and even Classical eras.")
            .put("ru", "Воины имет сравнительно неплохую силу и могут быть полезны в Древней и даже Классической эрах.");

    public static final L10n WORKERS_NAME = new L10n()
            .put("en", "Workers")
            .put("ru", "Рабочие");

    public static final L10n WORKERS_DESCRIPTION = new L10n()
            .put("en", "Workers can build improvements on tiles")
            .put("ru", "Рабочие могут строить улучшения на клетках");
}
