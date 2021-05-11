package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.L10n.L10n;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.RU;

public class L10nSkill {

    public static final L10n CAN_CONQUER_CITY = new L10n()
        .put(EN, "Can conquer a city")
        .put(RU, "Может захватить город");

    public static final L10n STRIKE_ON_PLAIN_TERRAIN = new L10n()
        .put(EN, "Strike on a plain terrain")
        .put(RU, "Удар на плоской местности");

    public static final L10n STRIKE_ON_ROUGH_TERRAIN = new L10n()
        .put(EN, "Strike on a rough terrain")
        .put(RU, "Удар на пересеченной местности");

    public static final L10n DEFENSE_AGAINST_RANGED_ATTACK= new L10n()
        .put(EN, "Defense against a ranged attack")
        .put(RU, "Защита от удаленной аттаки");

    public static final L10n DEFENSE_AGAINST_MELEE_ATTACK= new L10n()
        .put(EN, "Defense against a melee attack")
        .put(RU, "Защита от прямой аттаки");
}
