package com.tsoft.civilization.combat;

import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.unit.UnitCollection;
import com.tsoft.civilization.world.economic.Supply;

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

    public CombatStrength() { }

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

    public CombatStrength setMeleeAttackStrength(int meleeAttackStrength) {
        this.meleeAttackStrength = meleeAttackStrength;
        return this;
    }

    public int getStrength() {
        return strength;
    }

    public CombatStrength setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public int getRangedAttackStrength() {
        return rangedAttackStrength;
    }

    public CombatStrength setRangedAttackStrength(int rangedAttackStrength) {
        this.rangedAttackStrength = rangedAttackStrength;
        return this;
    }

    public int getRangedAttackRadius() {
        return rangedAttackRadius;
    }

    public CombatStrength setRangedAttackRadius(int rangedAttackRadius) {
        this.rangedAttackRadius = rangedAttackRadius;
        return this;
    }

    public boolean canConquerCity() {
        return canConquerCity;
    }

    public CombatStrength setCanConquerCity(boolean canConquerCity) {
        this.canConquerCity = canConquerCity;
        return this;
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
    public int calcStrikeStrength(int strikeStrength, HasCombatStrength target) {
        int strikeSkillsPercent = calcAttackerStrikeSkillsPercent(attacker, target);
        int strikeGeneralsPercent = getGreatGeneralCount(attacker) * 33;
        int strikeTerrainPercent = 0;
        int strikeAgainstThatTypePercent = 0;
        int strikeHappinessPercent = calcAttackerStrikeHappinessPercent();

        return strikeStrength + attackExperience +
                (int)Math.round(((double)strikeStrength * (double)strikeSkillsPercent ) / 100.0) +
                (int)Math.round(((double)strikeStrength * (double)strikeGeneralsPercent ) / 100.0) +
                (int)Math.round(((double)strikeStrength * (double)strikeTerrainPercent ) / 100.0) +
                (int)Math.round(((double)strikeStrength * (double)strikeAgainstThatTypePercent ) / 100.0) +
                (int)Math.round(((double)strikeStrength * (double)strikeHappinessPercent ) / 100.0);
    }

    private int calcAttackerStrikeSkillsPercent(HasCombatStrength attacker, HasCombatStrength target) {
        int value = 0;
        for (AbstractSkill skill : attacker.getSkills()) {
            value += skill.getAttackStrikePercent(attacker, target);
        }
        return value;
    }

    private int calcAttackerStrikeHappinessPercent() {
        int value = 0;

        Supply supply = attacker.getCivilization().calcSupply();
        if (supply.getHappiness() < -30) {
            value -= 33;
        } else
        if (supply.getHappiness() < -15) {
            value -= 15;
        } else
        if (supply.getHappiness() > 15) {
            value += 15;
        } else
        if (supply.getHappiness() > 30) {
            value += 33;
        }
        return value;
    }

    /** Calc the target's back strike strength (strike strength on defense) */
    public int calcTargetBackFireStrength(HasCombatStrength target) {
        int strikeOnDefenseSkillPercent = calcTargetStrikeOnDefenseSkillsPercent(target);
        int strikeOnDefenseGeneralsPercent = getGreatGeneralCount(attacker) * 10;
        int strikeOnDefenseTerrainPercent = 0;
        int strikeOnDefenseAgainstThatTypePercent = 0;

        return targetBackFireStrength +
                (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseSkillPercent ) / 100.0) +
                (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseGeneralsPercent ) / 100.0) +
                (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseTerrainPercent ) / 100.0) +
                (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseAgainstThatTypePercent ) / 100.0);
    }

    public CombatStrength setTargetBackFireStrength(int targetBackFireStrength) {
        this.targetBackFireStrength = targetBackFireStrength;
        return this;
    }

    private int calcTargetStrikeOnDefenseSkillsPercent(HasCombatStrength target) {
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
