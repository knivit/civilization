package com.tsoft.civilization.combat;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitMoveService;
import com.tsoft.civilization.unit.action.MoveUnitAction;
import com.tsoft.civilization.unit.action.UnitMoveResult;
import com.tsoft.civilization.util.AbstractDir;
import com.tsoft.civilization.world.World;

import java.util.List;

public class MeleeCombatService {

    private static final BaseCombatService baseCombatService = new BaseCombatService();
    private static final UnitMoveService unitMoveService = new UnitMoveService();

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
            UnitMoveResult moveResult = unitMoveService.getMoveOnAttackResult((AbstractUnit)attacker, target.getLocation());
            if (moveResult.isFailed()) {
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
                .skippedAsNoTargetsToAttack(true)
                .build();
        }

        // attacker must be able to pass to target's tile
        AbstractUnit unit = (AbstractUnit) attacker;
        UnitMoveResult moveResult = unitMoveService.getMoveOnAttackResult(unit, target.getLocation());
        if (moveResult.isFailed()) {
            return CombatResult.builder()
                .skippedAsMeleeNotEnoughPassScore(true)
                .build();
        }

        // calc the strength of the attack
        int meleeAttackStrength = attacker.getCombatStrength().getMeleeAttackStrength();
        int strikeStrength = baseCombatService.calcStrikeStrength(attacker, meleeAttackStrength, target);

        return baseCombatService.attack(attacker, target, strikeStrength);
    }
}
