package com.tsoft.civilization.combat;

import com.tsoft.civilization.StringParser;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class CombatResultMock {

    private static final String STRIKE_STRENGTH = "SS";
    private static final String APPLIED_STRIKE_STRENGTH = "AS";

    private static final String DEFENSE_STRENGTH = "DS";
    private static final String DEFENSE_STRENGTH_AFTER_ATTACK = "DSA";

    private static final String RANGED_EXPERIENCE = "RE";
    private static final String RANGED_EXPERIENCE_AFTER_ATTACK = "REA";

    private static final String MELEE_EXPERIENCE = "ME";
    private static final String MELEE_EXPERIENCE_AFTER_ATTACK = "MEA";

    private static final String DEFENSE_EXPERIENCE = "DE";
    private static final String DEFENSE_EXPERIENCE_AFTER_ATTACK = "DEA";

    private static final String IS_DESTROYED = "D";

    private static final Set<String> AVAILABLE_IDENTIFIERS = Set.of(
        STRIKE_STRENGTH, APPLIED_STRIKE_STRENGTH,
        DEFENSE_STRENGTH, DEFENSE_STRENGTH_AFTER_ATTACK,
        RANGED_EXPERIENCE, RANGED_EXPERIENCE_AFTER_ATTACK, MELEE_EXPERIENCE, MELEE_EXPERIENCE_AFTER_ATTACK,
        DEFENSE_EXPERIENCE, DEFENSE_EXPERIENCE_AFTER_ATTACK,
        IS_DESTROYED
    );

    public static CombatResult done(String attacker, String target) {
        return of(attacker, target, CombatStatus.DONE);
    }

    public static CombatResult failedRangedUndershoot(String attacker, String target) {
        return of(attacker, target, CombatStatus.FAILED_RANGED_UNDERSHOOT);
    }

    public static CombatResult failedNoTargets(String attacker, String target) {
        return of(attacker, target, CombatStatus.FAILED_NO_TARGETS);
    }

    private static CombatResult of(String attacker, String target, CombatStatus combatStatus) {
        StringParser parser = new StringParser();

        CombatantState attackerState = build(parser.parse(attacker, AVAILABLE_IDENTIFIERS));
        CombatantState targetState = build(parser.parse(target, AVAILABLE_IDENTIFIERS));

        return CombatResult.builder()
            .attackerState(attackerState)
            .targetState(targetState)
            .status(combatStatus)
            .build();
    }

    private static CombatantState build(Map<String, Integer> map) {
        int strikeStrength = map.getOrDefault(STRIKE_STRENGTH, 0);
        int appliedStrikeStrength = map.getOrDefault(APPLIED_STRIKE_STRENGTH, 0);
        int defenseStrength = map.getOrDefault(DEFENSE_STRENGTH, 0);
        int defenseStrengthAfterAttack = map.getOrDefault(DEFENSE_STRENGTH_AFTER_ATTACK, 0);
        int rangedExperience = map.getOrDefault(RANGED_EXPERIENCE, 0);
        int rangedExperienceAfterAttack = map.getOrDefault(RANGED_EXPERIENCE_AFTER_ATTACK, 0);
        int meleeExperience = map.getOrDefault(MELEE_EXPERIENCE, 0);
        int meleeExperienceAfterAttack = map.getOrDefault(MELEE_EXPERIENCE_AFTER_ATTACK, 0);
        int defenseExperience = map.getOrDefault(DEFENSE_EXPERIENCE, 0);
        int defenseExperienceAfterAttack = map.getOrDefault(DEFENSE_EXPERIENCE_AFTER_ATTACK, 0);
        int isDestroyed = map.getOrDefault(IS_DESTROYED, 0);

        return CombatantState.builder()
            .strikeStrength(strikeStrength)
            .appliedStrikeStrength(appliedStrikeStrength)
            .defenseStrength(defenseStrength)
            .defenseStrengthAfterAttack(defenseStrengthAfterAttack)
            .rangedExperience(rangedExperience)
            .rangedExperienceAfterAttack(rangedExperienceAfterAttack)
            .meleeExperience(meleeExperience)
            .meleeExperienceAfterAttack(meleeExperienceAfterAttack)
            .defenseExperience(defenseExperience)
            .defenseExperienceAfterAttack(defenseExperienceAfterAttack)
            .isDestroyed(isDestroyed == 1)
            .build();
    }

    private static final Comparator<CombatResult> comparator = (a, b) -> equals(a, b) ? 0 : 1;

    public static int compare(CombatResult a, CombatResult b) {
        return comparator.compare(a, b);
    }

    public static boolean equals(CombatResult a, CombatResult b) {
        boolean isEqual =
            // attacker
            a.getAttackerState().getStrikeStrength() == b.getAttackerState().getStrikeStrength() &&
            a.getAttackerState().getAppliedStrikeStrength() == b.getAttackerState().getAppliedStrikeStrength() &&
            a.getAttackerState().getDefenseStrength() == b.getAttackerState().getDefenseStrength() &&
            a.getAttackerState().getDefenseStrengthAfterAttack() == b.getAttackerState().getDefenseStrengthAfterAttack() &&
            a.getAttackerState().getRangedExperience() == b.getAttackerState().getRangedExperience() &&
            a.getAttackerState().getRangedExperienceAfterAttack() == b.getAttackerState().getRangedExperienceAfterAttack() &&
            a.getAttackerState().getMeleeExperience() == b.getAttackerState().getMeleeExperience() &&
            a.getAttackerState().getMeleeExperienceAfterAttack() == b.getAttackerState().getMeleeExperienceAfterAttack() &&
            a.getAttackerState().getDefenseExperience() == b.getAttackerState().getDefenseExperience() &&
            a.getAttackerState().getDefenseExperienceAfterAttack() == b.getAttackerState().getDefenseExperienceAfterAttack() &&
            a.getAttackerState().isDestroyed() == b.getAttackerState().isDestroyed() &&

            // target
            a.getTargetState().getStrikeStrength() == b.getTargetState().getStrikeStrength() &&
            a.getTargetState().getAppliedStrikeStrength() == b.getTargetState().getAppliedStrikeStrength() &&
            a.getTargetState().getDefenseStrength() == b.getTargetState().getDefenseStrength() &&
            a.getTargetState().getDefenseStrengthAfterAttack() == b.getTargetState().getDefenseStrengthAfterAttack() &&
            a.getTargetState().getRangedExperience() == b.getTargetState().getRangedExperience() &&
            a.getTargetState().getRangedExperienceAfterAttack() == b.getTargetState().getRangedExperienceAfterAttack() &&
            a.getTargetState().getMeleeExperience() == b.getTargetState().getMeleeExperience() &&
            a.getTargetState().getMeleeExperienceAfterAttack() == b.getTargetState().getMeleeExperienceAfterAttack() &&
            a.getTargetState().getDefenseExperience() == b.getTargetState().getDefenseExperience() &&
            a.getTargetState().getDefenseExperienceAfterAttack() == b.getTargetState().getDefenseExperienceAfterAttack() &&
            a.getTargetState().isDestroyed() == b.getTargetState().isDestroyed() &&

            // status
            a.getStatus() == b.getStatus();

        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.printf("""
                Type                        |   Actual   | Expected
                -----------------------------------------------------
                Attacker
                -----------------------------------------------------
                StrikeStrength              |     %5d  |    %5d | %s
                AppliedStrikeStrength       |     %5d  |    %5d | %s
                DefenseStrength             |     %5d  |    %5d | %s
                DefenseStrengthAfterAttack  |     %5d  |    %5d | %s
                RangedExperience            |     %5d  |    %5d | %s
                RangedExperienceAfterAttack |     %5d  |    %5d | %s
                MeleeExperience             |     %5d  |    %5d | %s
                MeleeExperienceAfterAttack  |     %5d  |    %5d | %s
                DefenseExperience           |     %5d  |    %5d | %s
                DefenseExperienceAfterAttack|     %5d  |    %5d | %s
                Destroyed                   |     %5b  |    %5b | %s
                -----------------------------------------------------
                Target
                -----------------------------------------------------
                StrikeStrength              |     %5d  |    %5d | %s
                AppliedStrikeStrength       |     %5d  |    %5d | %s
                DefenseStrength             |     %5d  |    %5d | %s
                DefenseStrengthAfterAttack  |     %5d  |    %5d | %s
                RangedExperience            |     %5d  |    %5d | %s
                RangedExperienceAfterAttack |     %5d  |    %5d | %s
                MeleeExperience             |     %5d  |    %5d | %s
                MeleeExperienceAfterAttack  |     %5d  |    %5d | %s
                DefenseExperience           |     %5d  |    %5d | %s
                DefenseExperienceAfterAttack|     %5d  |    %5d | %s
                Destroyed                   |     %5b  |    %5b | %s
                -----------------------------------------------------
                Status                      |     %5d  |    %5d | %s
                %n""",

                a.getAttackerState().getStrikeStrength(), b.getAttackerState().getStrikeStrength(), cmp(a.getAttackerState().getStrikeStrength(), b.getAttackerState().getStrikeStrength()),
                a.getAttackerState().getAppliedStrikeStrength(), b.getAttackerState().getAppliedStrikeStrength(), cmp(a.getAttackerState().getAppliedStrikeStrength(), b.getAttackerState().getAppliedStrikeStrength()),
                a.getAttackerState().getDefenseStrength(), b.getAttackerState().getDefenseStrength(), cmp(a.getAttackerState().getDefenseStrength(), b.getAttackerState().getDefenseStrength()),
                a.getAttackerState().getDefenseStrengthAfterAttack(), b.getAttackerState().getDefenseStrengthAfterAttack(), cmp(a.getAttackerState().getDefenseStrengthAfterAttack(), b.getAttackerState().getDefenseStrengthAfterAttack()),
                a.getAttackerState().getRangedExperience(), b.getAttackerState().getRangedExperience(), cmp(a.getAttackerState().getRangedExperience(), b.getAttackerState().getRangedExperience()),
                a.getAttackerState().getRangedExperienceAfterAttack(), b.getAttackerState().getRangedExperienceAfterAttack(), cmp(a.getAttackerState().getRangedExperienceAfterAttack(), b.getAttackerState().getRangedExperienceAfterAttack()),
                a.getAttackerState().getMeleeExperience(), b.getAttackerState().getMeleeExperience(), cmp(a.getAttackerState().getMeleeExperience(), b.getAttackerState().getMeleeExperience()),
                a.getAttackerState().getMeleeExperienceAfterAttack(), b.getAttackerState().getMeleeExperienceAfterAttack(), cmp(a.getAttackerState().getMeleeExperienceAfterAttack(), b.getAttackerState().getMeleeExperienceAfterAttack()),
                a.getAttackerState().getDefenseExperience(), b.getAttackerState().getDefenseExperience(), cmp(a.getAttackerState().getDefenseExperience(), b.getAttackerState().getDefenseExperience()),
                a.getAttackerState().getDefenseExperienceAfterAttack(), b.getAttackerState().getDefenseExperienceAfterAttack(), cmp(a.getAttackerState().getDefenseExperienceAfterAttack(), b.getAttackerState().getDefenseExperienceAfterAttack()),
                a.getAttackerState().isDestroyed(), b.getAttackerState().isDestroyed(), cmp(a.getAttackerState().isDestroyed() ? 1 : 0, b.getAttackerState().isDestroyed() ? 1 : 0),

                a.getTargetState().getStrikeStrength(), b.getTargetState().getStrikeStrength(), cmp(a.getTargetState().getStrikeStrength(), b.getTargetState().getStrikeStrength()),
                a.getTargetState().getAppliedStrikeStrength(), b.getTargetState().getAppliedStrikeStrength(), cmp(a.getTargetState().getAppliedStrikeStrength(), b.getTargetState().getAppliedStrikeStrength()),
                a.getTargetState().getDefenseStrength(), b.getTargetState().getDefenseStrength(), cmp(a.getTargetState().getDefenseStrength(), b.getTargetState().getDefenseStrength()),
                a.getTargetState().getDefenseStrengthAfterAttack(), b.getTargetState().getDefenseStrengthAfterAttack(), cmp(a.getTargetState().getDefenseStrengthAfterAttack(), b.getTargetState().getDefenseStrengthAfterAttack()),
                a.getTargetState().getRangedExperience(), b.getTargetState().getRangedExperience(), cmp(a.getTargetState().getRangedExperience(), b.getTargetState().getRangedExperience()),
                a.getTargetState().getRangedExperienceAfterAttack(), b.getTargetState().getRangedExperienceAfterAttack(), cmp(a.getTargetState().getRangedExperienceAfterAttack(), b.getTargetState().getRangedExperienceAfterAttack()),
                a.getTargetState().getMeleeExperience(), b.getTargetState().getMeleeExperience(), cmp(a.getTargetState().getMeleeExperience(), b.getTargetState().getMeleeExperience()),
                a.getTargetState().getMeleeExperienceAfterAttack(), b.getTargetState().getMeleeExperienceAfterAttack(), cmp(a.getTargetState().getMeleeExperienceAfterAttack(), b.getTargetState().getMeleeExperienceAfterAttack()),
                a.getTargetState().getDefenseExperience(), b.getTargetState().getDefenseExperience(), cmp(a.getTargetState().getDefenseExperience(), b.getTargetState().getDefenseExperience()),
                a.getTargetState().getDefenseExperienceAfterAttack(), b.getTargetState().getDefenseExperienceAfterAttack(), cmp(a.getTargetState().getDefenseExperienceAfterAttack(), b.getTargetState().getDefenseExperienceAfterAttack()),
                a.getTargetState().isDestroyed(), b.getTargetState().isDestroyed(), cmp(a.getTargetState().isDestroyed() ? 1 : 0, b.getTargetState().isDestroyed() ? 1 : 0),

                a.getStatus().ordinal(), b.getStatus().ordinal(), cmp(a.getStatus().ordinal(), b.getStatus().ordinal())
            );
        }

        return isEqual;
    }

    private static String cmp(int a, int b) {
        return (a == b) ? "" : "*";
    }
}
