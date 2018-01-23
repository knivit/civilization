package com.tsoft.civilization.unit;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.web.view.unit.WorkersView;
import com.tsoft.civilization.world.Civilization;

import java.util.UUID;

/**
 * Movement: 2;
 * Strength: 0;
 * Ranged Strength: 0;
 * Cost: 70 hammers;
 * Requires Resource: none
 * Technology: (none)
 *
 * Abilities: May create or repair land-based tile improvements. May clear Forest in 3 turns,
 * Jungle in 6 turns, and Marsh in 5 turns (with appropriate technologies).
 * Notes: Workers can build various tile improvements, build roads and railroads, clear forests and jungle,
 * and drain marshes. They can also embark on water like other land units (with the proper promotion).
 * There is no suspension of city growth during production as there is with the Settler,
 * so you may construct a Worker right away without hampering your new city's starting growth.
 * As civilian units, Workers have no defense and so are captured if attacked by an enemy unit.
 */
public class Workers extends AbstractUnit<WorkersView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();
    public static final Workers INSTANCE = new Workers();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength(0, 0, 20, 0, 0, false);
    private static final WorkersView VIEW = new WorkersView();

    @Override
    public UnitType getUnitType() {
        return UnitType.WORKERS;
    }

    @Override
    public UnitKind getUnitKind() {
        return UnitKind.CIVIL;
    }

    @Override
    protected CombatStrength getBaseCombatStrength() {
        return COMBAT_STRENGTH;
    }

    @Override
    public void initPassScore() {
        setPassScore(5);
    }

    @Override
    public WorkersView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public int getProductionCost() {
        return 40;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return true;
    }

    @Override
    public int getGoldCost() {
        return 200;
    }
}
