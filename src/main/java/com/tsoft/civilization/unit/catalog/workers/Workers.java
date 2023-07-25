package com.tsoft.civilization.unit.catalog.workers;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.civilization.Civilization;

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

    public static final String CLASS_UUID = UnitName.WORKERS.name();

    private static final UnitBaseState BASE_STATE = new WorkersBaseState().getBaseState();

    private static final AbstractUnitView VIEW = new WorkersView();

    public Workers(Civilization civilization) {
        super(civilization);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public UnitBaseState getBaseState() {
        return BASE_STATE;
    }

    @Override
    public AbstractUnitView getView() {
        return VIEW;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return true;
    }
}
