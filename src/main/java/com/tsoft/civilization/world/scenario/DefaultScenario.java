package com.tsoft.civilization.world.scenario;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

/**
 * The default scenario for a new civilization
 * - Mandatory Settlers unit added
 * - Optional Warriors unit added
 * - Relations with oll other civilizations set to neutral
 */
@Slf4j
public class DefaultScenario implements Scenario {

    @Override
    public ScenarioApplyResult apply(Civilization civilization) {
        if (!addFirstUnits(civilization)) {
            return ScenarioApplyResult.FAIL;
        }

        // set NEUTRAL state for this civilization with others
        World world = civilization.getWorld();
        for (Civilization otherCivilization : world.getCivilizations()) {
            if (!civilization.equals(otherCivilization)) {
                world.setCivilizationsRelations(civilization, otherCivilization, CivilizationsRelations.neutral());
            }
        }

        return ScenarioApplyResult.SUCCESS;
    }

    // Returns TRUE on success
    private boolean addFirstUnits(Civilization civilization) {
        World world = civilization.getWorld();

        // find a location for the Settlers
        Point settlersLocation = world.getSettlersStartLocation(civilization);
        if (settlersLocation == null) {
            log.warn("Can't place Settlers");
            return false;
        }

        // create the Settlers unit
        Settlers settlers = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        if (!civilization.units().addUnit(settlers, settlersLocation)) {
            log.warn("Can't place Settlers");
            return false;
        }

        // try to place Warriors near the Settlers
        Point warriorsLocation = world.getWarriorsStartLocation(civilization, settlersLocation);
        if (warriorsLocation == null) {
            log.warn("Can't place Warriors");
        }

        return true;
    }
}
