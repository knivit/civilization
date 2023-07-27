package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.*;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.world.World;

/**
 * A military unit can pillage
 * - a trade route
 * - an improvement on the tile
 */
public class PillageService {

    public static final ActionSuccessResult CAN_PILLAGE = new ActionSuccessResult(L10nCombat.CAN_PILLAGE);
    public static final ActionSuccessResult TARGET_PILLAGED = new ActionSuccessResult(L10nCombat.TARGET_PILLAGED);

    public static final ActionFailureResult PILLAGE_FAILED = new ActionFailureResult(L10nCombat.PILLAGE_FAILED);
    public static final ActionFailureResult NO_TARGETS_TO_PILLAGE = new ActionFailureResult(L10nCombat.NO_TARGETS_TO_PILLAGE);
    public static final ActionFailureResult ATTACKER_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult NOT_MILITARY_UNIT = new ActionFailureResult(L10nUnit.NOT_MILITARY_UNIT);

    private final CombatResult FAILED_NO_TARGETS = CombatResult.builder()
        .status(CombatStatus.FAILED_NO_TARGETS)
        .build();

    private final CombatResult PILLAGED = CombatResult.builder()
        .status(CombatStatus.DONE)
        .build();

    public ActionAbstractResult pillage(HasCombatStrength attacker, HasCombatStrength target) {
        ActionAbstractResult result = canPillage(attacker);
        if (result.isFail()) {
            return result;
        }

        CombatResult combatResult = doPillage(attacker, target);
        if (CombatStatus.FAILED_NO_TARGETS.equals(combatResult.getStatus())) {
            return NO_TARGETS_TO_PILLAGE;
        }

        if (!CombatStatus.DONE.equals(combatResult.getStatus())) {
            return PILLAGE_FAILED;
        }

        return TARGET_PILLAGED;
    }

    public ActionAbstractResult canPillage(HasCombatStrength attacker) {
        if (attacker == null || attacker.isDestroyed()) {
            return ATTACKER_NOT_FOUND;
        }

        if (!attacker.getUnitCategory().isMilitary()) {
            return NOT_MILITARY_UNIT;
        }

        HasCombatStrengthList targets = getTargetsToPillage(attacker);
        if (targets == null || targets.isEmpty()) {
            return NO_TARGETS_TO_PILLAGE;
        }

        return CAN_PILLAGE;
    }

    public HasCombatStrengthList getTargetsToPillage(HasCombatStrength attacker) {
        AbstractUnit unit = (AbstractUnit)attacker;

        // The attacker must be a military unit
        if (!unit.getUnitCategory().isMilitary()) {
            return null;
        }

        AbstractTerrain tile = unit.getTile();
        World world = unit.getWorld();

        // Civilizations must be at war or the tile is nobody's
        Civilization owner = world.getTileService().getCivilizationOnTile(tile);
        if (owner != null && !world.isWar(unit.getCivilization(), owner)) {
            return null;
        }

        HasCombatStrengthList list = null;
        for (AbstractImprovement improvement : tile.getImprovements()) {
            if (!improvement.isDestroyed()) {
                list = (list == null) ? new HasCombatStrengthList() : list;
                list.add(improvement);
            }
        }

        // TODO add trade routes for this tile

        return list;
    }

    private CombatResult doPillage(HasCombatStrength attacker, HasCombatStrength target) {
        if (target.isDestroyed()) {
            return FAILED_NO_TARGETS;
        }

        Supply supply = target.calcPillageSupply();
        attacker.getCivilization().pillageReceived(target, supply);
        target.destroy();

        return PILLAGED;
    }
}
