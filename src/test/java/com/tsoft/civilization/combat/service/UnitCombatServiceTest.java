package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.CombatStrengthMock;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class UnitCombatServiceTest {

    /**
     * Scenario:
     * A civil unit (Settlers) stays on a plain terrain (a grassland)
     */
    @Test
    public void calc_settlers_combat_strength() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .settlers("settlers", new Point(2, 0))
        );

        world.startGame();

        Settlers settlers = world.get("settlers");

        assertThat(settlers.getCombatService().calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:0 RAR:0 RAE:0 RBS:0",
                "MAL:0 MAS:0 MAE:0 MBS:0",
                "DL:0 DS:0 DE:0",
                "D:0"
            ));
    }

    /**
     * Scenario:
     * A ranged unit (Archers) stays on a hill with forest
     */
    @Test
    public void calc_archers_combat_strength() {
        MockWorld world = MockWorld.newWorldWithFeatures();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .archers("archers", new Point(2, 0))
        );

        world.startGame();

        Archers archers = world.get("archers");

        assertThat(archers.getCombatService().calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:13 RAR:2 RAE:0 RBS:0",
                "MAL:0 MAS:0 MAE:0 MBS:0",
                "DL:0 DS:7 DE:0",
                "D:0"
            ));
    }
}
