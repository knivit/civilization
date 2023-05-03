package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.combat.CombatResult;
import com.tsoft.civilization.combat.CombatStatus;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.unit.AbstractUnit;

/**
 *
 * A military unit can pillage
 * - a trade route
 * - an improvement on the tile
 */
public class PillageCombatService {

    private final CombatResult FAILED_NO_TARGETS = CombatResult.builder()
        .status(CombatStatus.FAILED_NO_TARGETS)
        .build();

    private final CombatResult PILLAGED = CombatResult.builder()
        .status(CombatStatus.DONE)
        .build();

    public CombatResult attack(HasCombatStrength attacker) {
        HasCombatStrengthList targets = getTargetsToPillage(attacker);
        if (targets == null || targets.isEmpty()) {
            return FAILED_NO_TARGETS;
        }

        for (HasCombatStrength target : targets) {
            AbstractImprovement improvement = (AbstractImprovement) target;
            if (!improvement.isDestroyed()) {
                improvement.destroy();
                return PILLAGED;
            }
        }

        return FAILED_NO_TARGETS;
    }

    public HasCombatStrengthList getTargetsToPillage(HasCombatStrength attacker) {
        AbstractUnit unit = (AbstractUnit)attacker;
        AbstractTerrain tile = unit.getTile();

        HasCombatStrengthList list = null;
        for (AbstractImprovement improvement : tile.getImprovements()) {
            if (!improvement.isDestroyed()) {
                list = (list == null) ? new HasCombatStrengthList() : list;
                list.add(improvement);
            }
        }

        return list;
    }
}
