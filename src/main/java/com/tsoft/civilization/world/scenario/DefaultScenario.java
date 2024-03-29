package com.tsoft.civilization.world.scenario;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.catalog.settlers.Settlers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

import static com.tsoft.civilization.civilization.L10nCivilization.BARBARIANS;

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

        // set NEUTRAL state for this civilization with others;
        // with Barbarians there will be WAR
        World world = civilization.getWorld();
        for (Civilization otherCivilization : world.getCivilizations()) {
            if (civilization.equals(otherCivilization)) {
                continue;
            }

            if (BARBARIANS.equals(otherCivilization.getName())) {
                world.setCivilizationsRelations(civilization, otherCivilization, CivilizationsRelations.war());
            } else {
                world.setCivilizationsRelations(civilization, otherCivilization, CivilizationsRelations.neutral());
            }
        }

        return ScenarioApplyResult.SUCCESS;
    }

    // Returns TRUE on success
    private boolean addFirstUnits(Civilization civilization) {
        World world = civilization.getWorld();

        // find a location for the Settlers
        Point settlersLocation = civilization.getSettlerService().getStartLocation();
        if (settlersLocation == null) {
            log.warn("No locations to place Settlers");
            return false;
        }

        // create the Settlers unit
        AbstractUnit settlers = UnitFactory.newInstance(civilization, "Settler");
        if (!civilization.getUnitService().addUnit(settlers, settlersLocation)) {
            log.warn("Can't add Settlers to a civilization");
            return false;
        }

        // try to place Warriors near the Settlers
        Point warriorsLocation = civilization.getUnitService().getWarriorsStartLocation(civilization, settlersLocation);
        if (warriorsLocation == null) {
            log.warn("Can't place Warriors");
        }

        return true;
    }
}
