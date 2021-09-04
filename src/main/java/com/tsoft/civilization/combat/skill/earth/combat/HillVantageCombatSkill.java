package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.L10nSkill;
import com.tsoft.civilization.combat.skill.SkillLevel;
import com.tsoft.civilization.combat.skill.SkillType;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Point;
import lombok.Getter;

import static com.tsoft.civilization.world.Year.*;

/**
 * Vantage - If the city is constructed (or a unit is placed) on a Hill terrain, it has an advantage over attackers,
 * which translates into additional strength (both defense and attack)
 */
public class HillVantageCombatSkill implements AbstractCombatSkill {

    public static final AbstractCombatSkill HILL_VANTAGE_COMBAT_SKILL = new HillVantageCombatSkill();

    @Getter
    private final L10n localizedName = L10nSkill.HILL_VANTAGE_COMBAT_SKILL;

    @Getter
    private final SkillType skillType = SkillType.ACCUMULATOR;

    private HillVantageCombatSkill() { }

    @Override
    public CombatStrength getCombatStrength(HasCombatStrength unit, SkillLevel level) {
        Point location = unit.getLocation();
        AbstractTile tile = unit.getCivilization().getWorld().getTilesMap().getTile(location);

        if (tile.hasFeature(Hill.class)) {
            UnitCategory category = unit.getUnitCategory();
            if (category.isCity()) {
                return getCityAttackStrength(unit).add(getCityDefenseStrength(unit));
            }

            if (category.isRanged()) {
                return getRangedUnitAttackStrength(unit).add(getRangedUnitDefenseStrength(unit));
            }

            if (category.isMelee()) {
                return getMeleeUnitAttackStrength(unit).add(getMeleeUnitDefenseStrength(unit));
            }
        }

        return CombatStrength.ZERO;
    }

    private CombatStrength getCityAttackStrength(HasCombatStrength unit) {
        int era = unit.getCivilization().getWorld().getEra();

        int strength = switch (era) {
            case ANCIENT_ERA -> 20;
            case CLASSICAL_ERA -> 30;
            case MEDIEVAL_ERA -> 40;
            case RENAISSANCE_ERA -> 50;
            case INDUSTRIAL_ERA -> 150;
            case MODERN_ERA -> 200;
            default -> 0;
        };

        return CombatStrength.builder().rangedAttackStrength(strength).build();
    }

    private CombatStrength getRangedUnitAttackStrength(HasCombatStrength unit) {
        int era = unit.getCivilization().getWorld().getEra();

        int strength = switch (era) {
            case ANCIENT_ERA -> 6;
            case CLASSICAL_ERA -> 10;
            case MEDIEVAL_ERA -> 14;
            case RENAISSANCE_ERA -> 18;
            case INDUSTRIAL_ERA -> 50;
            case MODERN_ERA -> 20;
            default -> 0;
        };

        return CombatStrength.builder().rangedAttackStrength(strength).build();
    }

    private CombatStrength getMeleeUnitAttackStrength(HasCombatStrength unit) {
        int era = unit.getCivilization().getWorld().getEra();

        int strength = switch (era) {
            case ANCIENT_ERA -> 2;
            case CLASSICAL_ERA -> 4;
            case MEDIEVAL_ERA -> 4;
            case RENAISSANCE_ERA -> 6;
            case INDUSTRIAL_ERA -> 6;
            case MODERN_ERA -> 4;
            default -> 0;
        };

        return CombatStrength.builder().meleeAttackStrength(strength).build();
    }

    private CombatStrength getCityDefenseStrength(HasCombatStrength unit) {
        int era = unit.getCivilization().getWorld().getEra();

        int strength = switch (era) {
            case ANCIENT_ERA -> 10;
            case CLASSICAL_ERA -> 20;
            case MEDIEVAL_ERA -> 30;
            case RENAISSANCE_ERA -> 50;
            case INDUSTRIAL_ERA -> 100;
            case MODERN_ERA -> 100;
            case ATOMIC_ERA -> 0;
            case INFORMATION_ERA -> 0;
            default -> 0;
        };

        return CombatStrength.builder().defenseStrength(strength).build();
    }

    private CombatStrength getRangedUnitDefenseStrength(HasCombatStrength unit) {
        int era = unit.getCivilization().getWorld().getEra();

        int strength = switch (era) {
            case ANCIENT_ERA -> 2;
            case CLASSICAL_ERA -> 5;
            case MEDIEVAL_ERA -> 7;
            case RENAISSANCE_ERA -> 10;
            case INDUSTRIAL_ERA -> 30;
            case MODERN_ERA -> 50;
            case ATOMIC_ERA -> 0;
            case INFORMATION_ERA -> 0;
            default -> 0;
        };

        return CombatStrength.builder().defenseStrength(strength).build();
    }

    private CombatStrength getMeleeUnitDefenseStrength(HasCombatStrength unit) {
        int era = unit.getCivilization().getWorld().getEra();

        int strength = switch (era) {
            case ANCIENT_ERA -> 2;
            case CLASSICAL_ERA -> 5;
            case MEDIEVAL_ERA -> 7;
            case RENAISSANCE_ERA -> 10;
            case INDUSTRIAL_ERA -> 30;
            case MODERN_ERA -> 50;
            case ATOMIC_ERA -> 0;
            case INFORMATION_ERA -> 0;
            default -> 0;
        };

        return CombatStrength.builder().defenseStrength(strength).build();
    }
}
