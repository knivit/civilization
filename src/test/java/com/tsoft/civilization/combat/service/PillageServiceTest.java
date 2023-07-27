package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.catalog.farm.Farm;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.catalog.warriors.Warriors;
import com.tsoft.civilization.unit.catalog.workers.Workers;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

class PillageServiceTest {

    private static final PillageService pillageService = new PillageService();

    /**
     * Scenario:
     *   Russia has a city and two units: workers and warriors
     *   America has a city and a farm improvement
     */
    @Test
    void get_targets_to_pillage() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 3 ",
            "-+--------",
            "0|. g g . ",
            "1| . g g .",
            "2|. . g . ",
            "3| . . . ."));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .workers("workers", Point.of(1, 1))
            .warriors("warriors", Point.of(2, 1))
        );
        Workers workers = world.get("workers");
        Warriors warriors = world.get("warriors");

        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .city("New York", Point.of(2, 2))
            .improvement("farm1", workers.getLocation(), Farm.CLASS_UUID)
            .improvement("farm2", warriors.getLocation(), Farm.CLASS_UUID)
        );

        // At neutral relations there are no targets to pillage
        assertThat(world.getCivilizationsRelations(russia, america)).isEqualTo(CivilizationsRelations.neutral());
        assertThat(pillageService.getTargetsToPillage(workers)).isNull();
        assertThat(pillageService.getTargetsToPillage(warriors)).isNull();

        // Start a war
        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        assertThat(pillageService.getTargetsToPillage(workers)).isNull();
        assertThat(pillageService.getTargetsToPillage(warriors)).isNotEmpty()
            .map(HasCombatStrength::getLocation)
            .containsExactlyInAnyOrder(warriors.getLocation());
    }
}