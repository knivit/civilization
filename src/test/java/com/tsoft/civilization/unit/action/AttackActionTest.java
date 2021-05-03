package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.combat.CombatService;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.combat.CombatService.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AttackActionTest {

    private final MockWorld world = MockWorld.newSimpleWorld();

    @Test
    public void attacker_not_found() {
        CombatService combatService = mock(CombatService.class);
        AttackAction attackAction = new AttackAction(combatService);

        assertThat(attackAction.attack(null, null)).isEqualTo(ATTACKER_NOT_FOUND);
    }

    @Test
    public void destroyed_attacker_not_found() {
        CombatService combatService = mock(CombatService.class);
        AttackAction attackAction = new AttackAction(combatService);

        Civilization russia = world.createCivilization(RUSSIA);
        Warriors warriors = UnitFactory.newInstance(russia, Warriors.CLASS_UUID);
        warriors.destroy();

        assertThat(attackAction.attack(warriors, null)).isEqualTo(ATTACKER_NOT_FOUND);
    }

    @Test
    public void attacker_not_military() {
        CombatService combatService = mock(CombatService.class);
        AttackAction attackAction = new AttackAction(combatService);

        Civilization russia = world.createCivilization(RUSSIA);
        Workers workers = UnitFactory.newInstance(russia, Workers.CLASS_UUID);

        assertThat(attackAction.attack(workers, null)).isEqualTo(NOT_MILITARY_UNIT);
    }

    @Test
    public void no_targets_to_attack() {
        CombatService combatService = mock(CombatService.class);
        AttackAction attackAction = new AttackAction(combatService);

        Civilization russia = world.createCivilization(RUSSIA);
        Warriors warriors = UnitFactory.newInstance(russia, Warriors.CLASS_UUID);

        assertThat(attackAction.attack(warriors, null)).isEqualTo(NO_TARGETS_TO_ATTACK);
    }

    @Test
    public void invalid_location() {
        CombatService combatService = mock(CombatService.class);
        AttackAction attackAction = new AttackAction(combatService);

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        Warriors warriors = (Warriors) world.unit("warriors");

        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(2, 2)));
        Workers foreignWorkers = (Workers) world.unit("foreignWorkers");

        when(combatService.getTargetsToAttack(eq(warriors))).thenReturn(HasCombatStrengthList.of(foreignWorkers));

        assertThat(attackAction.attack(warriors, null))
            .isEqualTo(INVALID_LOCATION);
    }
}
