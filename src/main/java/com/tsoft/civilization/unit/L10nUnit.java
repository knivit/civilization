package com.tsoft.civilization.unit;

import com.tsoft.civilization.L10n.L10n;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.RU;

public class L10nUnit {
    public static final L10n NO_UNITS = new L10n()
        .put(EN, "No Units")
        .put(RU, "Юнитов нет");

    public static final L10n UNITS_ON_TILE = new L10n()
        .put(EN, "Units on the Tile")
        .put(RU, "Юниты на этой ячейке");

    public static final L10n NAME = new L10n()
        .put(EN, "Name")
        .put(RU, "Имя");

    public static final L10n UNIT_NOT_FOUND = new L10n()
        .put(EN, "Unit not found")
        .put(RU, "Юнит не найден");

    public static final L10n COMBAT_STRENGTH = new L10n()
        .put(EN, "Combat Strength")
        .put(RU, "Сила в нападении");

    public static final L10n MOVE_NAME = new L10n()
        .put(EN, "Move")
        .put(RU, "Идти");

    public static final L10n MOVE_DESCRIPTION = new L10n()
        .put(EN, "Move unit to a tile")
        .put(RU, "Переместить юнит в другую ячейку");

    public static final L10n ATTACK_NAME = new L10n()
        .put(EN, "Attack")
        .put(RU, "Атака");

    public static final L10n ATTACK_DESCRIPTION = new L10n()
        .put(EN, "Attack foreign unit or a city")
        .put(RU, "Атаковать иностранный юнит или город");

    public static final L10n CAPTURE_NAME = new L10n()
        .put(EN, "Capture")
        .put(RU, "Захватить");

    public static final L10n CAPTURE_DESCRIPTION = new L10n()
        .put(EN, "Capture foreign civil unit")
        .put(RU, "Захватить иностранный гражданский юнит");

    public static final L10n DESTROY_UNIT_NAME = new L10n()
        .put(EN, "Destroy Unit")
        .put(RU, "Разрашить юнит");

    public static final L10n DESTROY_UNIT_DESCRIPTION = new L10n()
        .put(EN, "Destroy unit and save your money on unit's expenses")
        .put(RU, "Уничтожить юнит и сохранить деньги от расходов на содержание юнита");

    /** Events */

    public static final L10n UNIT_WAS_DESTROYED_EVENT = new L10n()
        .put(EN, "Unit %s was destroyed")
        .put(RU, "Юнит %s был уничтожен");

    public static final L10n UNIT_HAS_WON_ATTACK_EVENT = new L10n()
        .put(EN, "Unit has won the attack")
        .put(RU, "Юнит выиграл атаку");

    public static final L10n UNIT_MOVED_EVENT = new L10n()
        .put(EN, "Unit has moved")
        .put(RU, "Юнит переместился");

    public static final L10n ATTACK_DONE_EVENT = new L10n()
        .put(EN, "Attack done")
        .put(RU, "Атака выполнена");

    /** Actions */

    public static final L10n UNIT_WAS_DESTROYED = new L10n()
        .put(EN, "Unit was destroyed")
        .put(RU, "Юнит был уничтожен");

    public static final L10n INVALID_LOCATION = new L10n()
        .put(EN, "Invalid location")
        .put(RU, "Неверное местоположение");

    public static final L10n NO_LOCATIONS_TO_MOVE = new L10n()
        .put(EN, "There are no locations to move in")
        .put(RU, "Нет возможных ячеек для перемещения");

    public static final L10n NO_TARGETS_TO_ATTACK = new L10n()
        .put(EN, "There are no targets to attack")
        .put(RU, "Нет целей для атаки");

    public static final L10n ATTACK_SKIPPED = new L10n()
        .put(EN, "The attack did not take place")
        .put(RU, "Атака не состоялась");

    public static final L10n MELEE_NOT_ENOUGH_PASS_SCORE = new L10n()
        .put(EN, "Can't move on target's tile")
        .put(RU, "Невозможно перейти на ячейку цели");

    public static final L10n UNIT_MOVED = new L10n()
        .put(EN, "Unit moved")
        .put(RU, "Юнит перемещен");

    public static final L10n CAN_MOVE = new L10n()
        .put(EN, "Unit can move there")
        .put(RU, "Юнит может быть перемещен");

    public static final L10n CAN_ATTACK = new L10n()
        .put(EN, "Unit can attack")
        .put(RU, "Юнит может выполнить атаку");

    public static final L10n RANGED_UNDERSHOOT = new L10n()
        .put(EN, "Distance too long or missile can't overcome it")
        .put(RU, "Расстояние слишком большое или снаряд не может преодолеть его");

    public static final L10n TARGET_DESTROYED = new L10n()
        .put(EN, "Target is destroyed")
        .put(RU, "Цель уничтожена");

    public static final L10n ATTACKED = new L10n()
        .put(EN, "Attack is done")
        .put(RU, "Атака завершена");

    public static final L10n FOREIGN_UNIT_CAPTURED = new L10n()
        .put(EN, "Foreign unit is captured")
        .put(RU, "Иностранный юнит захвачен");

    public static final L10n ATTACKER_IS_DESTROYED_DURING_ATTACK = new L10n()
        .put(EN, "The attacker was destroyed during the attack")
        .put(RU, "Атакующий был уничтожен в ходе атаки");

    public static final L10n NO_LOCATIONS_TO_CAPTURE = new L10n()
        .put(EN, "There is nothing to capture")
        .put(RU, "Для захвате ничего нет");

    public static final L10n CAN_CAPTURE = new L10n()
        .put(EN, "There are units to capture")
        .put(RU, "Есть юниты для захвата");

    public static final L10n NOTHING_TO_CAPTURE = new L10n()
        .put(EN, "There is nothing to capture at that location")
        .put(RU, "Нечего захватывать в данном месте");

    public static final L10n UNIT_DESTROYED = new L10n()
        .put(EN, "The unit is destroyed")
        .put(RU, "Юнит уничтожен");

    public static final L10n CAN_DESTROY_UNIT = new L10n()
        .put(EN, "The unit can be destroyed")
        .put(RU, "Юнит может быть удален");

    public static final L10n NOT_MILITARY_UNIT = new L10n()
        .put(EN, "Not a military unit")
        .put(RU, "Не военный юнит");

    public static final L10n NO_PASS_SCORE = new L10n()
        .put(EN, "Not enough the passing score")
        .put(RU, "Недостаточно ходов перемещения");

    public static final L10n LAST_SETTLERS_CANT_BE_DESTROYED = new L10n()
        .put(EN, "The only settlers unit can not be destroyed")
        .put(RU, "The only settlers unit can not be destroyed");

    public static final L10n UNIT_WAS_BOUGHT = new L10n()
        .put(EN, "An Unit was bought")
        .put(RU, "Юнит куплен");

    /** Web */

    public static final L10n FEATURES = new L10n()
        .put(EN, "Features")
        .put(RU, "Характеристики");

    public static final L10n BUILD = new L10n()
        .put(EN, "Build")
        .put(RU, "Построить");

    public static final L10n BUILD_DESCRIPTION = new L10n()
        .put(EN, "Construct a unit")
        .put(RU, "Построить юнит");

    public static final L10n BUY = new L10n()
        .put(EN, "Buy")
        .put(RU, "Купить");

    public static final L10n BUY_DESCRIPTION = new L10n()
        .put(EN, "Buy a unit")
        .put(RU, "Купить юнит");

    public static final L10n STRENGTH = new L10n()
        .put(EN, "Defense Strength")
        .put(RU, "Сила в защите");

    public static final L10n STRENGTH_HEADER = new L10n()
        .put(EN, "DS")
        .put(RU, "СЗ");

    public static final L10n MELEE_ATTACK_STRENGTH = new L10n()
        .put(EN, "Melee Attack Strength")
        .put(RU, "Сила атаки в ближнем бою");

    public static final L10n MELEE_ATTACK_STRENGTH_HEADER = new L10n()
        .put(EN, "MAS")
        .put(RU, "САБ");

    public static final L10n CAN_CONQUER_CITY = new L10n()
        .put(EN, "Can conquer a city ?")
        .put(RU, "Может захватывать города ?");

    public static final L10n ATTACK_EXPERIENCE = new L10n()
        .put(EN, "Attack Experience")
        .put(RU, "Опыт атак");

    public static final L10n DEFENSE_EXPERIENCE = new L10n()
        .put(EN, "Defense Experience")
        .put(RU, "Опыт защит");

    public static final L10n RANGED_ATTACK_STRENGTH = new L10n()
        .put(EN, "Ranged Attack Strength")
        .put(RU, "Сила атаки в дальнем бою");

    public static final L10n RANGED_ATTACK_STRENGTH_HEADER = new L10n()
        .put(EN, "RAS")
        .put(RU, "САД");

    public static final L10n RANGED_ATTACK_RADIUS = new L10n()
        .put(EN, "Ranged Attack Radius")
        .put(RU, "Радиус атаки в дальнем бою");

    public static final L10n PASS_SCORE = new L10n()
        .put(EN, "Pass Score")
        .put(RU, "Оставшиеся ходы");

    public static final L10n PASS_SCORE_HEADER = new L10n()
        .put(EN, "P")
        .put(RU, "Х");

    /** Units List */

    public static final L10n ARCHERS_NAME = new L10n()
        .put(EN, "Archers")
        .put(RU, "Лучники");

    public static final L10n ARCHERS_DESCRIPTION = new L10n()
        .put(EN, "May not melee attack. The bombardment range for archers is 2 hexes.")
        .put(RU, "Не может атаковать в ближнем бою. Радиус обстрела - 2 ячейки.");

    public static final L10n GREAT_ARTIST_NAME = new L10n()
        .put(EN, "Great Artist")
        .put(RU, "Великий художник");

    public static final L10n GREAT_ARTIST_DESCRIPTION = new L10n()
        .put(EN, "Great Artist can paint a city which gives additional happiness")
        .put(RU, "Великий художник может украсить город и он станет приносить больше радости");

    public static final L10n GREAT_ENGINEER_NAME = new L10n()
        .put(EN, "Great Engineer")
        .put(RU, "Великий инженер");

    public static final L10n GREAT_ENGINEER_DESCRIPTION = new L10n()
        .put(EN, "Great Engineer can build an improvement which gives labour points")
        .put(RU, "Великий инженер способен построить усовершенствование, которое будет приносить дополнительный труд");

    public static final L10n GREAT_GENERAL_NAME = new L10n()
        .put(EN, "Great General")
        .put(RU, "Великий генерал");

    public static final L10n GREAT_GENERAL_DESCRIPTION = new L10n()
        .put(EN, "Great General can build a defense improvement on a tile or capture foreign tiles")
        .put(RU, "Великий генерал может построить оборонительное улучшение на клетке или захватить вражеские клетки");

    public static final L10n GREAT_MERCHANT_NAME = new L10n()
        .put(EN, "Great Merchant")
        .put(RU, "Великий торговец");

    public static final L10n GREAT_MERCHANT_DESCRIPTION = new L10n()
        .put(EN, "Great Merchant can improve relations with state-city")
        .put(RU, "Великий торговец поможет наладить отношения с городом-государством");

    public static final L10n GREAT_SCIENTIST_NAME = new L10n()
        .put(EN, "Great Scientist")
        .put(RU, "Великий ученый");

    public static final L10n GREAT_SCIENTIST_DESCRIPTION = new L10n()
        .put(EN, "Great Scientist can open a new technology")
        .put(RU, "Великий ученый может открыть новую технологию");

    public static final L10n SETTLERS_NAME = new L10n()
        .put(EN, "Settlers")
        .put(RU, "Поселенцы");

    public static final L10n SETTLERS_DESCRIPTION = new L10n()
        .put(EN, "Founds new cities to expand your empire. Growth of the City is stopped while this unit is being built.")
        .put(RU, "Расширяет вашу империю, основывая новые города. Рост города остановлен, пока этот юнит строится.");

    public static final L10n WARRIORS_NAME = new L10n()
        .put(EN, "Warriors")
        .put(RU, "Воины");

    public static final L10n WARRIORS_DESCRIPTION = new L10n()
        .put(EN, "The Warrior has a fairly decent combat strength, and can be useful in the Ancient and even Classical eras.")
        .put(RU, "Воины имет сравнительно неплохую силу и могут быть полезны в Древней и даже Классической эрах.");

    public static final L10n WORKERS_NAME = new L10n()
        .put(EN, "Workers")
        .put(RU, "Рабочие");

    public static final L10n WORKERS_DESCRIPTION = new L10n()
        .put(EN, "Workers can build improvements on tiles")
        .put(RU, "Рабочие могут строить улучшения на клетках");
}
