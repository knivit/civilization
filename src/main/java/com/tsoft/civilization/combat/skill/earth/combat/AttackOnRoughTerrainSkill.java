package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Point;
import lombok.Getter;

import static com.tsoft.civilization.world.Year.*;

/**
 * For ranged units +15% to attack strength when the attacker is placed on a rough terrain
 */
public class AttackOnRoughTerrainSkill implements AbstractCombatSkill {

    public static final AbstractCombatSkill ATTACK_ON_ROUGH_TERRAIN_SKILL = new AttackOnRoughTerrainSkill();

    @Getter
    private final SkillName skillName = SkillName.ATTACK_ON_ROUGH_TERRAIN;

    @Getter
    private final L10n localizedName = L10nSkill.ATTACK_ON_ROUGH_TERRAIN;

    @Getter
    private final SkillType skillType = SkillType.ACCUMULATOR;

    private AttackOnRoughTerrainSkill() { }

    @Override
    public CombatStrength getCombatStrength(HasCombatStrength unit, SkillLevel level) {
        Point attackerLocation = unit.getLocation();
        AbstractTerrain tile = unit.getCivilization().getTilesMap().getTile(attackerLocation);
        TerrainType terrainType = tile.getTileType();

        if (TerrainType.EARTH_ROUGH.equals(terrainType)) {
            if (tile.hasFeature(Hill.class)) {
                UnitCategory category = unit.getUnitCategory();
                if (category.isRanged() && !category.isCity()) {
                    return getRangedUnitAttackStrength(unit, level);
                }
            }
        }

        return CombatStrength.ZERO;
    }

    private CombatStrength getRangedUnitAttackStrength(HasCombatStrength unit, SkillLevel level) {
        CombatStrength combatStrength = unit.getBaseCombatStrength(unit.getCivilization());

        int era = unit.getCivilization().getYear().getEra();
        int strength = switch (era) {
            case ANCIENT_ERA, CLASSICAL_ERA, MEDIEVAL_ERA, RENAISSANCE_ERA -> (int)Math.round(combatStrength.getRangedAttackStrength() * 1.15);
            default -> 0;
        };

        return CombatStrength.builder().rangedAttackStrength(strength * level.getValue()).build();
    }
}
