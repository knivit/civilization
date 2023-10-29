package com.tsoft.civilization.combat.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.combat.service.AttackService;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.helper.html.HtmlDocument;
import com.tsoft.civilization.helper.html.HtmlParser;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.combat.service.AttackService.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AttackActionTest {

    @Test
    void get_html() {
        AttackService attackService = mock(AttackService.class);
        AttackAction attackAction = new AttackAction(attackService);

        MockWorld world = MockWorld.newSimpleWorld();
        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        AbstractUnit warriors = world.get("warriors");

        world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(2, 2)));
        AbstractUnit foreignWorkers = world.get("foreignWorkers");

        when(attackService.canAttack(eq(warriors))).thenReturn(CAN_ATTACK);
        when(attackService.getTargetsToAttack(eq(warriors))).thenReturn(new HasCombatStrengthList().add(foreignWorkers));

        StringBuilder buf = attackAction.getHtml(warriors);
        HtmlDocument actual = HtmlParser.parse(buf);

        HtmlDocument expected = HtmlParser.parse(Format.text("""
            <td>
                <button onclick="client.unitAttackAction({ attacker:'$warriorsId', ucol:'2', urow:'0', 'locations':[{'col':'2','row':'2'}] })">Attack</button>
            </td>
            <td>Attack foreign unit or a city</td>
            """,

            "$warriorsId", warriors.getId()));

        assertThat(actual).isEqualTo(expected);
    }
}
