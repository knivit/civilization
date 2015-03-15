package com.tsoft.civilization.combat;

import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.world.economic.CivilizationScore;

public class CombatStrength {
    // Melee units' attack strength
    private int meleeAttackStrength;

    // Any units' back fire
    private int targetBackFireStrength;

    // Overall unit's strength
    private int strength;

    // Unit's ranged attack strength
    private int rangedAttackStrength;

    // Ranged attack radius
    private int rangedAttackRadius;

    // Experience during an attack/defense
    private int attackExperience;
    private int defenseExperience;

    // Unit's options
    private boolean canConquerCity;

    // Was destroyed during a step
    private boolean isDestroyed;

    // Owner of this
    private HasCombatStrength attacker;

    public CombatStrength(int meleeAttackStrength, int targetBackFireStrength, int strength, int rangedAttackStrength,
                          int rangedAttackRadius, boolean canConquerCity) {
        this.meleeAttackStrength = meleeAttackStrength;
        this.targetBackFireStrength = targetBackFireStrength;
        this.strength = strength;
        this.rangedAttackStrength = rangedAttackStrength;
        this.rangedAttackRadius = rangedAttackRadius;

        this.canConquerCity = canConquerCity;
    }

    public CombatStrength(HasCombatStrength attacker, CombatStrength combatStrength) {
        this.attacker = attacker;

        meleeAttackStrength = combatStrength.meleeAttackStrength;
        targetBackFireStrength = combatStrength.targetBackFireStrength;
        strength = combatStrength.strength;
        rangedAttackStrength = combatStrength.rangedAttackStrength;
        rangedAttackRadius = combatStrength.rangedAttackRadius;

        canConquerCity = combatStrength.canConquerCity;
    }

    public int getMeleeAttackStrength() {
        return meleeAttackStrength;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getRangedAttackStrength() {
        return rangedAttackStrength;
    }

    public int getRangedAttackRadius() {
        return rangedAttackRadius;
    }

    public boolean canConquerCity() {
        return canConquerCity;
    }

    public int getAttackExperience() {
        return attackExperience;
    }

    public void addAttackExperience(int value) {
        attackExperience += value;
    }

    public int getDefenseExperience() {
        return defenseExperience;
    }

    public void addDefenseExperience(int value) {
        defenseExperience += value;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    /** Calc the strike strength */
    public int getStrikeStrength(int strikeStrength, HasCombatStrength target) {
        int strikeSkillsPercent = getAttackerStrikeSkillsPercent(attacker, target);
        int strikeGeneralsPercent = getGreatGeneralCount(attacker) * 33;
        int strikeTerrainPercent = 0;
        int strikeAgainstThatTypePercent = 0;
        int strikeHappinessPercent = getAttackerStrikeHappinessPercent();

        return strikeStrength + attackExperience +
                (int)Math.round(((double)strikeStrength * (double)strikeSkillsPercent ) / 100.0) +
                (int)Math.round(((double)strikeStrength * (double)strikeGeneralsPercent ) / 100.0) +
                (int)Math.round(((double)strikeStrength * (double)strikeTerrainPercent ) / 100.0) +
                (int)Math.round(((double)strikeStrength * (double)strikeAgainstThatTypePercent ) / 100.0) +
                (int)Math.round(((double)strikeStrength * (double)strikeHappinessPercent ) / 100.0);
    }

    private int getAttackerStrikeSkillsPercent(HasCombatStrength attacker, HasCombatStrength target) {
        int value = 0;
        for (AbstractSkill skill : attacker.getSkills()) {
            value += skill.getAttackStrikePercent(attacker, target);
        }
        return value;
    }

    private int getAttackerStrikeHappinessPercent() {
        int value = 0;
        CivilizationScore score = attacker.getCivilization().getCivilizationScore();
        if (score.getHappiness() < -30) {
            value -= 33;
        } else
        if (score.getHappiness() < -15) {
            value -= 15;
        } else
        if (score.getHappiness() > 15) {
            value += 15;
        } else
        if (score.getHappiness() > 30) {
            value += 33;
        }
        return value;
    }

    /** Calc the target's back strike strength (strike strength on defense) */
    public int getTargetBackFireStrength(HasCombatStrength target) {
        int strikeOnDefenseSkillPercent = getTargetStrikeOnDefenseSkillsPercent(target);
        int strikeOnDefenseGeneralsPercent = getGreatGeneralCount(attacker) * 10;
        int strikeOnDefenseTerrainPercent = 0;
        int strikeOnDefenseAgainstThatTypePercent = 0;

        return targetBackFireStrength +
                (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseSkillPercent ) / 100.0) +
                (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseGeneralsPercent ) / 100.0) +
                (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseTerrainPercent ) / 100.0) +
                (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseAgainstThatTypePercent ) / 100.0);
    }

    private int getTargetStrikeOnDefenseSkillsPercent(HasCombatStrength target) {
        int value = 0;
        for (AbstractSkill skill : target.getSkills()) {
            value += skill.getTargetStrikeOnDefensePercent(attacker, target);
        }
        return value;
    }

    private int getGreatGeneralCount(HasCombatStrength unit) {
        UnitCollection unitsAround = unit.getUnitsAround(2);
        return unitsAround.getGreatGeneralCount();
    }
}
