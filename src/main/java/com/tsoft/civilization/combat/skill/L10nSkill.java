package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class L10nSkill {

    public static final L10n BASE_MOVEMENT_SKILL = new L10n()
        .put(EN, "Base movement skill")
        .put(RU, "Базовое перемещение");

    public static final L10n CITY_GARRISON_COMBAT_SKILL = new L10n()
        .put(EN, "City's garrison combat skill")
        .put(RU, "Сила городского военного гарризона");

    public static final L10n CITY_BUILDINGS_COMBAT_SKILL = new L10n()
        .put(EN, "City's buildings defense skill")
        .put(RU, "Защита города защитными зданиями");

    public static final L10n CITY_POPULATION_COMBAT_SKILL = new L10n()
        .put(EN, "City's population defense skill")
        .put(RU, "Защита города населением");

    public static final L10n BASE_HEALING_SKILL = new L10n()
        .put(EN, "Base healing skill")
        .put(RU, "Базовое восстановление");

    public static final L10n HILL_VANTAGE_COMBAT_SKILL = new L10n()
        .put(EN, "Bonus for hill-stationed units")
        .put(RU, "Очки для юнитов на холме");

    public static final L10n CAN_CONQUER_CITY = new L10n()
        .put(EN, "Can conquer a city")
        .put(RU, "Может захватить город");

    public static final L10n ATTACK_ON_PLAIN_TERRAIN = new L10n()
        .put(EN, "Strike on a plain terrain")
        .put(RU, "Удар на плоской местности");

    public static final L10n ATTACK_ON_ROUGH_TERRAIN = new L10n()
        .put(EN, "Strike on a rough terrain")
        .put(RU, "Удар на пересеченной местности");

    public static final L10n DEFENSE_AGAINST_RANGED_ATTACK= new L10n()
        .put(EN, "Defense against a ranged attack")
        .put(RU, "Защита от удаленной аттаки");

    public static final L10n DEFENSE_AGAINST_MELEE_ATTACK= new L10n()
        .put(EN, "Defense against a melee attack")
        .put(RU, "Защита от прямой аттаки");
}
