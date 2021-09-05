package com.tsoft.civilization.combat;

import com.tsoft.civilization.StringParser;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class CombatStrengthMock {


    private static final String RANGED_ATTACK_LEVEL = "RAL";
    private static final String RANGED_ATTACK_STRENGTH = "RAS";
    private static final String RANGED_ATTACK_RADIUS = "RAR";
    private static final String RANGED_ATTACK_EXPERIENCE = "RAE";
    private static final String RANGED_BACK_FIRE_STRENGTH = "RBS";

    private static final String MELEE_ATTACK_LEVEL = "MAL";
    private static final String MELEE_ATTACK_STRENGTH = "MAS";
    private static final String MELEE_ATTACK_EXPERIENCE = "MAE";
    private static final String MELEE_BACK_FIRE_STRENGTH = "MBS";

    private static final String DEFENSE_LEVEL = "DL";
    private static final String DEFENSE_STRENGTH = "DS";
    private static final String DEFENSE_EXPERIENCE = "DE";

    private static final String IS_DESTROYED = "D";

    private static final Set<String> AVAILABLE_IDENTIFIERS = Set.of(
        RANGED_ATTACK_LEVEL, RANGED_ATTACK_STRENGTH, RANGED_ATTACK_RADIUS, RANGED_ATTACK_EXPERIENCE, RANGED_BACK_FIRE_STRENGTH,
        MELEE_ATTACK_LEVEL, MELEE_ATTACK_STRENGTH, MELEE_ATTACK_EXPERIENCE, MELEE_BACK_FIRE_STRENGTH,
        DEFENSE_LEVEL, DEFENSE_STRENGTH, DEFENSE_EXPERIENCE,
        IS_DESTROYED
    );

    public static CombatStrength of(String ranged, String melee, String defense, String options) {
        StringParser parser = new StringParser();

        return build(parser.parse(ranged + " " + melee + " " + defense + " " + options, AVAILABLE_IDENTIFIERS));
    }

    private static CombatStrength build(Map<String, Integer> map) {
        // Ranged attack
        int rangedAttackLevel = map.getOrDefault(RANGED_ATTACK_LEVEL, 0);
        int rangedAttackStrength = map.getOrDefault(RANGED_ATTACK_STRENGTH, 0);
        int rangedAttackRadius = map.getOrDefault(RANGED_ATTACK_RADIUS, 0);
        int rangedAttackExperience = map.getOrDefault(RANGED_ATTACK_EXPERIENCE, 0);
        int rangedBackFireStrength = map.getOrDefault(RANGED_BACK_FIRE_STRENGTH, 0);

        // Melee attack
        int meleeAttackLevel = map.getOrDefault(MELEE_ATTACK_LEVEL, 0);
        int meleeAttackStrength = map.getOrDefault(MELEE_ATTACK_STRENGTH, 0);
        int meleeAttackExperience = map.getOrDefault(MELEE_ATTACK_EXPERIENCE, 0);
        int meleeBackFireStrength = map.getOrDefault(MELEE_BACK_FIRE_STRENGTH, 0);

        // Defense
        int defenseLevel = map.getOrDefault(DEFENSE_LEVEL, 0);
        int defenseStrength = map.getOrDefault(DEFENSE_STRENGTH, 0);
        int defenseExperience = map.getOrDefault(DEFENSE_EXPERIENCE, 0);

        // Options
        int isDestroyed = map.getOrDefault(IS_DESTROYED, 0);

        return CombatStrength.builder()
            .rangedAttackLevel(rangedAttackLevel)
            .rangedAttackStrength(rangedAttackStrength)
            .rangedAttackRadius(rangedAttackRadius)
            .rangedAttackExperience(rangedAttackExperience)
            .rangedBackFireStrength(rangedBackFireStrength)

            .meleeAttackLevel(meleeAttackLevel)
            .meleeAttackStrength(meleeAttackStrength)
            .meleeAttackExperience(meleeAttackExperience)
            .meleeBackFireStrength(meleeBackFireStrength)

            .defenseLevel(defenseLevel)
            .defenseStrength(defenseStrength)
            .defenseExperience(defenseExperience)

            .isDestroyed(isDestroyed == 1)
            .build();
    }

    private static final Comparator<CombatStrength> comparator = (a, b) -> equals(a, b) ? 0 : 1;

    public static int compare(CombatStrength a, CombatStrength b) {
        return comparator.compare(a, b);
    }

    public static boolean equals(CombatStrength a, CombatStrength b) {
        boolean isEqual =
            a.getRangedAttackLevel() == b.getRangedAttackLevel() &&
            a.getRangedAttackStrength() == b.getRangedAttackStrength() &&
            a.getRangedAttackRadius() == b.getRangedAttackRadius() &&
            a.getRangedAttackExperience() == b.getRangedAttackExperience() &&
            a.getRangedBackFireStrength() == b.getRangedBackFireStrength() &&

            a.getMeleeAttackLevel() == b.getMeleeAttackLevel() &&
            a.getMeleeAttackStrength() == b.getMeleeAttackStrength() &&
            a.getMeleeAttackExperience() == b.getMeleeAttackExperience() &&
            a.getMeleeBackFireStrength() == b.getMeleeBackFireStrength() &&

            a.getDefenseLevel() == b.getDefenseLevel() &&
            a.getDefenseStrength() == b.getDefenseStrength() &&
            a.getDefenseExperience() == b.getDefenseExperience() &&

            a.isDestroyed() == b.isDestroyed();


        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.printf("""
                Type                       |   Actual   | Expected
                ---------------------------------------------------
                RangedAttackLevel         |     %5d  |    %5d | %s
                RangedAttackStrength      |     %5.0f  |    %5.0f | %s
                RangedAttackRadius        |     %5d  |    %5d | %s
                RangedAttackExperience    |     %5.0f  |    %5.0f | %s
                RangedBackFireStrength    |     %5.0f  |    %5.0f | %s
                ---------------------------------------------------
                MeleeAttackLevel          |     %5d  |    %5d | %s
                MeleeAttackStrength       |     %5.0f  |    %5.0f | %s
                MeleeAttackExperience     |     %5.0f  |    %5.0f | %s
                MeleeBackFireStrength     |     %5.0f  |    %5.0f | %s
                ---------------------------------------------------
                DefenseLevel              |     %5d  |    %5d | %s
                DefenseStrength           |     %5.0f  |    %5.0f | %s
                DefenseExperience         |     %5.0f  |    %5.0f | %s
                ---------------------------------------------------
                Destroyed                 |     %5b  |    %5b | %s
                %n""",

                a.getRangedAttackLevel(), b.getRangedAttackLevel(), cmp(a.getRangedAttackLevel(), b.getRangedAttackLevel()),
                a.getRangedAttackStrength(), b.getRangedAttackStrength(), cmp(a.getRangedAttackStrength(), b.getRangedAttackStrength()),
                a.getRangedAttackRadius(), b.getRangedAttackRadius(), cmp(a.getRangedAttackRadius(), b.getRangedAttackRadius()),
                a.getRangedAttackExperience(), b.getRangedAttackExperience(), cmp(a.getRangedAttackExperience(), b.getRangedAttackExperience()),
                a.getRangedBackFireStrength(), b.getRangedBackFireStrength(), cmp(a.getRangedBackFireStrength(), b.getRangedBackFireStrength()),

                a.getMeleeAttackLevel(), b.getMeleeAttackLevel(), cmp(a.getMeleeAttackLevel(), b.getMeleeAttackLevel()),
                a.getMeleeAttackStrength(), b.getMeleeAttackStrength(), cmp(a.getMeleeAttackStrength(), b.getMeleeAttackStrength()),
                a.getMeleeAttackExperience(), b.getMeleeAttackExperience(), cmp(a.getMeleeAttackExperience(), b.getMeleeAttackExperience()),
                a.getMeleeBackFireStrength(), b.getMeleeBackFireStrength(), cmp(a.getMeleeBackFireStrength(), b.getMeleeBackFireStrength()),

                a.getDefenseLevel(), b.getDefenseLevel(), cmp(a.getDefenseLevel(), b.getDefenseLevel()),
                a.getDefenseStrength(), b.getDefenseStrength(), cmp(a.getDefenseStrength(), b.getDefenseStrength()),
                a.getDefenseExperience(), b.getDefenseExperience(), cmp(a.getDefenseExperience(), b.getDefenseExperience()),

                a.isDestroyed(), b.isDestroyed(), cmp(a.isDestroyed() ?  1 : 0, b.isDestroyed() ?  1 : 0)
            );
        }

        return isEqual;
    }

    private static String cmp(double a, double b) {
        return (Math.round(a) == Math.round(b)) ? "" : "*";
    }
}
