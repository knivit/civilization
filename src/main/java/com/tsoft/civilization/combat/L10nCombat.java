package com.tsoft.civilization.combat;

import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class L10nCombat {

    public static final L10n INVALID_TARGET_LOCATION = new L10n()
        .put(EN, "Invalid target's location")
        .put(RU, "Неверные координаты цели");

    public static final L10n CAN_ATTACK = new L10n()
        .put(EN, "Unit can attack")
        .put(RU, "Юнит может выполнить атаку");

    public static final L10n INVALID_ATTACK_TARGET = new L10n()
        .put(EN, "Can not attack this target")
        .put(RU, "Юнит не может атаковать эту цель");

    public static final L10n RANGED_UNDERSHOOT = new L10n()
        .put(EN, "Distance too long or missile can't overcome it")
        .put(RU, "Расстояние слишком большое или снаряд не может преодолеть его");

    public static final L10n TARGET_DESTROYED = new L10n()
        .put(EN, "Target is destroyed")
        .put(RU, "Цель уничтожена");

    public static final L10n ATTACKED = new L10n()
        .put(EN, "Attack is done")
        .put(RU, "Атака завершена");

    public static final L10n UNIT_CAPTURED = new L10n()
        .put(EN, "Foreign unit is captured")
        .put(RU, "Иностранный юнит захвачен");

    public static final L10n ATTACKER_IS_DESTROYED_DURING_ATTACK = new L10n()
        .put(EN, "The attacker was destroyed during the attack")
        .put(RU, "Атакующий был уничтожен в ходе атаки");

    public static final L10n NO_LOCATIONS_TO_CAPTURE = new L10n()
        .put(EN, "There is nothing to capture")
        .put(RU, "Для захвата в плен ничего нет");

    public static final L10n CAN_CAPTURE = new L10n()
        .put(EN, "There are units to capture")
        .put(RU, "Есть юниты для захвата");

    public static final L10n NOTHING_TO_CAPTURE = new L10n()
        .put(EN, "There is nothing to capture at that location")
        .put(RU, "Нечего захватывать в данном месте");

    public static final L10n NO_TARGETS_TO_ATTACK = new L10n()
        .put(EN, "There are no targets to attack")
        .put(RU, "Нет целей для атаки");

    public static final L10n ATTACK_FAILED = new L10n()
        .put(EN, "The attack did not take place")
        .put(RU, "Атака не состоялась");
}
