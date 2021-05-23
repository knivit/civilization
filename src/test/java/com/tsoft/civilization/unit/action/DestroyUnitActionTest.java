package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.helper.html.HtmlDocument;
import com.tsoft.civilization.helper.html.HtmlParser;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.service.destroy.DestroyUnitService;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;


import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.service.destroy.DestroyUnitService.CAN_DESTROY_UNIT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DestroyUnitActionTest {

    @Test
    public void get_html() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .workers("workers", new Point(1, 1)));

        world.startGame();

        AbstractUnit workers = world.unit("workers");

        DestroyUnitService destroyUnitService = mock(DestroyUnitService.class);
        when(destroyUnitService.canDestroy(any())).thenReturn(CAN_DESTROY_UNIT);

        DestroyUnitAction destroyUnitAction = new DestroyUnitAction(destroyUnitService);
        StringBuilder buf = destroyUnitAction.getHtml(workers);

        HtmlDocument expected = HtmlParser.parse(Format.text("""
            <td>
              <button onclick="client.destroyUnitAction({ unit:'$workersId' })">Destroy Unit</button>
            </td>
            <td>Destroy unit and save your money on unit's expenses</td>
            """,

            "$workersId", workers.getId()));

        HtmlDocument actual = HtmlParser.parse(buf);
        assertThat(actual).isEqualTo(expected);
    }
}
