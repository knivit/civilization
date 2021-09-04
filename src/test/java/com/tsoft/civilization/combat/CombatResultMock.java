package com.tsoft.civilization.combat;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class CombatResultMock {

    private static final String STRIKE_STRENGTH = "SS";
    private static final String APPLIED_STRIKE_STRENGTH = "AS";

    private static final String DEFENSE_STRENGTH = "DS";
    private static final String DEFENSE_STRENGTH_AFTER_ATTACK = "DSA";

    private static final String EXPERIENCE = "E";
    private static final String EXPERIENCE_AFTER_ATTACK = "EA";

    private static final String IS_DESTROYED = "D";

    private static final Set<String> AVAILABLE_IDENTIFIERS = Set.of(
        STRIKE_STRENGTH, APPLIED_STRIKE_STRENGTH,
        DEFENSE_STRENGTH, DEFENSE_STRENGTH_AFTER_ATTACK,
        EXPERIENCE, EXPERIENCE_AFTER_ATTACK,
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

        CombatantState attackerState = buildState(parser.parse(attacker, AVAILABLE_IDENTIFIERS));
        CombatantState targetState = buildState(parser.parse(target, AVAILABLE_IDENTIFIERS));

        return CombatResult.builder()
            .attackerState(attackerState)
            .targetState(targetState)
            .status(combatStatus)
            .build();
    }

    private static CombatantState buildState(Map<String, Integer> state) {
        int defenseStrength = state.getOrDefault(DEFENSE_STRENGTH, 0);
        int defenseStrengthAfterAttack = state.getOrDefault(DEFENSE_STRENGTH_AFTER_ATTACK, 0);
        int experience = state.getOrDefault(EXPERIENCE, 0);
        int experienceAfterAttack = state.getOrDefault(EXPERIENCE_AFTER_ATTACK, 0);
        int strikeStrength = state.getOrDefault(STRIKE_STRENGTH, 0);
        int isDestroyed = state.getOrDefault(IS_DESTROYED, 0);

        return CombatantState.builder()
            .defenseStrength(defenseStrength)
            .defenseStrengthAfterAttack(defenseStrengthAfterAttack)
            .experience(experience)
            .experienceAfterAttack(experienceAfterAttack)
            .strikeStrength(strikeStrength)
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
            a.getAttackerState().getExperience() == b.getAttackerState().getExperience() &&
            a.getAttackerState().getExperienceAfterAttack() == b.getAttackerState().getExperienceAfterAttack() &&

            // target
            a.getTargetState().getStrikeStrength() == b.getTargetState().getStrikeStrength() &&
            a.getTargetState().getAppliedStrikeStrength() == b.getTargetState().getAppliedStrikeStrength() &&
            a.getTargetState().getDefenseStrength() == b.getTargetState().getDefenseStrength() &&
            a.getTargetState().getDefenseStrengthAfterAttack() == b.getTargetState().getDefenseStrengthAfterAttack() &&
            a.getTargetState().getExperience() == b.getTargetState().getExperience() &&
            a.getTargetState().getExperienceAfterAttack() == b.getTargetState().getExperienceAfterAttack() &&

            // status
            a.getStatus() == b.getStatus();

        // log them out as jUnit doesn't show the values in assertTrue
        if (!isEqual) {
            System.out.printf("""
                Type                       |   Actual   | Expected
                ----------------------------------------------------
                Attacker
                ----------------------------------------------------
                StrikeStrength             |     %5d  |    %5d | %s
                AppliedStrikeStrength      |     %5d  |    %5d | %s
                DefenseStrength            |     %5d  |    %5d | %s
                DefenseStrengthAfterAttack |     %5d  |    %5d | %s
                Experience                 |     %5d  |    %5d | %s
                ExperienceAfterAttack      |     %5d  |    %5d | %s
                ----------------------------------------------------
                Target
                ----------------------------------------------------
                StrikeStrength             |     %5d  |    %5d | %s
                AppliedStrikeStrength      |     %5d  |    %5d | %s
                DefenseStrength            |     %5d  |    %5d | %s
                DefenseStrengthAfterAttack |     %5d  |    %5d | %s
                Experience                 |     %5d  |    %5d | %s
                ExperienceAfterAttack      |     %5d  |    %5d | %s
                ----------------------------------------------------
                Status                     |     %5d  |    %5d | %s
                %n""",

                a.getAttackerState().getStrikeStrength(), b.getAttackerState().getStrikeStrength(), cmp(a.getAttackerState().getStrikeStrength(), b.getAttackerState().getStrikeStrength()),
                a.getAttackerState().getAppliedStrikeStrength(), b.getAttackerState().getAppliedStrikeStrength(), cmp(a.getAttackerState().getAppliedStrikeStrength(), b.getAttackerState().getAppliedStrikeStrength()),
                a.getAttackerState().getDefenseStrength(), b.getAttackerState().getDefenseStrength(), cmp(a.getAttackerState().getDefenseStrength(), b.getAttackerState().getDefenseStrength()),
                a.getAttackerState().getDefenseStrengthAfterAttack(), b.getAttackerState().getDefenseStrengthAfterAttack(), cmp(a.getAttackerState().getDefenseStrengthAfterAttack(), b.getAttackerState().getDefenseStrengthAfterAttack()),
                a.getAttackerState().getExperience(), b.getAttackerState().getExperience(), cmp(a.getAttackerState().getExperience(), b.getAttackerState().getExperience()),
                a.getAttackerState().getExperienceAfterAttack(), b.getAttackerState().getExperienceAfterAttack(), cmp(a.getAttackerState().getExperienceAfterAttack(), b.getAttackerState().getExperienceAfterAttack()),

                a.getTargetState().getStrikeStrength(), b.getTargetState().getStrikeStrength(), cmp(a.getTargetState().getStrikeStrength(), b.getTargetState().getStrikeStrength()),
                a.getTargetState().getAppliedStrikeStrength(), b.getTargetState().getAppliedStrikeStrength(), cmp(a.getTargetState().getAppliedStrikeStrength(), b.getTargetState().getAppliedStrikeStrength()),
                a.getTargetState().getDefenseStrength(), b.getTargetState().getDefenseStrength(), cmp(a.getTargetState().getDefenseStrength(), b.getTargetState().getDefenseStrength()),
                a.getTargetState().getDefenseStrengthAfterAttack(), b.getTargetState().getDefenseStrengthAfterAttack(), cmp(a.getTargetState().getDefenseStrengthAfterAttack(), b.getTargetState().getDefenseStrengthAfterAttack()),
                a.getTargetState().getExperience(), b.getTargetState().getExperience(), cmp(a.getTargetState().getExperience(), b.getTargetState().getExperience()),
                a.getTargetState().getExperienceAfterAttack(), b.getTargetState().getExperienceAfterAttack(), cmp(a.getTargetState().getExperienceAfterAttack(), b.getTargetState().getExperienceAfterAttack()),

                a.getStatus().ordinal(), b.getStatus().ordinal(), cmp(a.getStatus().ordinal(), b.getStatus().ordinal())
            );
        }

        return isEqual;
    }

    private static String cmp(int a, int b) {
        return (a == b) ? "" : "*";
    }
}
