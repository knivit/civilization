package com.tsoft.civilization.unit.civil.workers;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.civilization.Civilization;
import lombok.Getter;

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
public class Workers extends AbstractUnit {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final CombatStrength COMBAT_STRENGTH = CombatStrength.builder()
        .defenseStrength(0)
        .build();

    @Getter
    private final int baseProductionCost = 40;

    private static final WorkersView VIEW = new WorkersView();

    public Workers(Civilization civilization) {
        super(civilization);
    }

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.CIVIL;
    }

    @Override
    public CombatStrength getBaseCombatStrength() {
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
    public boolean checkEraAndTechnology(Civilization civilization) {
        return true;
    }

    @Override
    public int getGoldCost() {
        return 200;
    }
}
