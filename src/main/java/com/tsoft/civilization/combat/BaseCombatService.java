package com.tsoft.civilization.combat;

import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.world.economic.Supply;

public class BaseCombatService {

    /** Calc the strike strength */
    public int calcStrikeStrength(HasCombatStrength attacker, int strikeStrength, HasCombatStrength target) {
        int strikeSkillsPercent = calcAttackerStrikeSkillsPercent(attacker, target);
        int strikeGeneralsPercent = getGreatGeneralCount(attacker) * 33;
        int strikeTerrainPercent = 0;
        int strikeAgainstThatTypePercent = 0;
        int strikeHappinessPercent = calcAttackerStrikeHappinessPercent(attacker);

        return strikeStrength + attacker.getCombatStrength().getAttackExperience() +
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

    private int calcAttackerStrikeHappinessPercent(HasCombatStrength attacker) {
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
    public int calcTargetBackFireStrength(HasCombatStrength attacker, HasCombatStrength target) {
        int strikeOnDefenseSkillPercent = calcTargetStrikeOnDefenseSkillsPercent(attacker, target);
        int strikeOnDefenseGeneralsPercent = getGreatGeneralCount(attacker) * 10;
        int strikeOnDefenseTerrainPercent = 0;
        int strikeOnDefenseAgainstThatTypePercent = 0;

        int targetBackFireStrength = target.getCombatStrength().getTargetBackFireStrength();
        return targetBackFireStrength +
            (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseSkillPercent ) / 100.0) +
            (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseGeneralsPercent ) / 100.0) +
            (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseTerrainPercent ) / 100.0) +
            (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseAgainstThatTypePercent ) / 100.0);
    }

    private int calcTargetStrikeOnDefenseSkillsPercent(HasCombatStrength attacker, HasCombatStrength target) {
        int value = 0;
        for (AbstractSkill skill : target.getSkills()) {
            value += skill.getTargetStrikeOnDefensePercent(attacker, target);
        }
        return value;
    }

    private int getGreatGeneralCount(HasCombatStrength unit) {
        UnitList unitsAround = unit.getUnitsAround(2);
        return unitsAround.getGreatGeneralCount();
    }
}
