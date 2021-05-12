package com.tsoft.civilization.combat.skill;

/**
 * Skills' types
 *   * ACCUMULATOR - (unit skills) applied to an unit and all such skills are accumulated
 *   * ATTACKER_MODIFIER - (victim skills) applied to the attacker after all ACCUMULATORS and added to attacker strength after that
 *   * VICTIM_MODIFIER - (attacker skills) applied to the victim after all ACCUMULATORS and added to victim strength after that
 */
public enum SkillType {

    ACCUMULATOR,
    ATTACKER_MODIFIER,
    VICTIM_MODIFIER
}
