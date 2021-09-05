package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.*;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.service.move.MoveUnitService;
import com.tsoft.civilization.util.AbstractDir;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;

import java.util.List;

public class MeleeCombatService {

    public static final ActionSuccessResult CAN_ATTACK = new ActionSuccessResult(L10nCombat.CAN_ATTACK);

    public static final ActionFailureResult NO_PASS_SCORE = new ActionFailureResult(L10nUnit.NO_PASS_SCORE);
    public static final ActionFailureResult INVALID_ATTACK_TARGET = new ActionFailureResult(L10nCombat.INVALID_ATTACK_TARGET);

    private static final BaseCombatService baseCombatService = new BaseCombatService();
    private static final MoveUnitService moveUnitService = new MoveUnitService();

    // Get all targets to fight (there can be more than one targets on a tile)
    public HasCombatStrengthList getTargetsToAttack(HasCombatStrength attacker) {
        HasCombatStrengthList targets = new HasCombatStrengthList();

        // Add foreign units and cities if its civilization is in war with our
        List<HasCombatStrength> targetsAround = baseCombatService.getPossibleTargetsAround(attacker, 1);
        Civilization myCivilization = attacker.getCivilization();
        World world = myCivilization.getWorld();

        for (HasCombatStrength target : targetsAround) {
            // check there is a war
            if (!world.isWar(myCivilization, target.getCivilization())) {
                continue;
            }

            // check we can move to the location
            ActionAbstractResult moveResult = getMoveOnMeleeAttackResult((AbstractUnit)attacker, target.getLocation());
            if (moveResult.isFail()) {
                continue;
            }

            targets.add(target);
        }

        return targets;
    }

    public CombatResult attack(HasCombatStrength attacker, HasCombatStrength target) {
        // target must be next to the attacker
        AbstractDir dir = attacker.getCivilization().getTilesMap().getDirToLocation(attacker.getLocation(), target.getLocation());
        if (dir == null) {
            return CombatResult.builder()
                .status(CombatStatus.FAILED_NO_TARGETS)
                .build();
        }

        // attacker must be able to pass to target's tile
        AbstractUnit unit = (AbstractUnit) attacker;
        ActionAbstractResult moveResult = getMoveOnMeleeAttackResult(unit, target.getLocation());
        if (moveResult.isFail()) {
            return CombatResult.builder()
                .status(CombatStatus.FAILED_MELEE_NOT_ENOUGH_PASS_SCORE)
                .build();
        }

        // calc the strength of the attack
        int meleeAttackStrength = attacker.calcCombatStrength().getMeleeAttackStrength();
        int strikeStrength = baseCombatService.calcStrikeStrength(attacker, meleeAttackStrength, target);

        return baseCombatService.attack(attacker, target, strikeStrength);
    }

    // Check can we move there during melee attack
    private ActionAbstractResult getMoveOnMeleeAttackResult(AbstractUnit unit, Point nextLocation) {
        // check is the passing score enough
        AbstractTile tile = unit.getTilesMap().getTile(nextLocation);
        int tilePassCost = moveUnitService.getPassCost(unit.getCivilization(), unit, tile);

        int passScore = unit.getPassScore();
        if (passScore < tilePassCost) {
            return NO_PASS_SCORE;
        }

        // can not attack own city
        City city = unit.getWorld().getCityAtLocation(nextLocation);
        if (city != null) {
            if (unit.getCivilization().equals(city.getCivilization())) {
                return INVALID_ATTACK_TARGET;
            }

            return CAN_ATTACK;
        }

        return CAN_ATTACK;
    }
}
