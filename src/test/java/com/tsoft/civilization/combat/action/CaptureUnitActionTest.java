package com.tsoft.civilization.combat.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.combat.service.CaptureUnitService;
import com.tsoft.civilization.helper.html.HtmlDocument;
import com.tsoft.civilization.helper.html.HtmlParser;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.combat.service.CaptureUnitService.CAN_CAPTURE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CaptureUnitActionTest {

    @Test
    public void get_html() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(1, 1)));
        AbstractUnit warriors = world.get("warriors");

        world.createCivilization(AMERICA, new MockScenario()
            .workers("workers", new Point(2, 1)));
        AbstractUnit workers = world.get("workers");

        world.startGame();

        CaptureUnitService captureUnitService = mock(CaptureUnitService.class);
        when(captureUnitService.canCapture(eq(warriors))).thenReturn(CAN_CAPTURE);
        when(captureUnitService.getLocationsToCapture(eq(warriors))).thenReturn(List.of(workers.getLocation()));

        CaptureUnitAction captureUnitAction = new CaptureUnitAction(captureUnitService);
        StringBuilder buf = captureUnitAction.getHtml(warriors);

        HtmlDocument expected = HtmlParser.parse(Format.text("""
            <td>
              <button onclick="client.captureUnitAction({ attacker:'$warriorsId', ucol:'1', urow:'1', 'locations':[{'col':'2','row':'1'}] })">Capture</button>
            </td>
            <td>Capture foreign civil unit</td>
            """,

            "$warriorsId", warriors.getId()));

        HtmlDocument actual = HtmlParser.parse(buf);
        assertThat(actual).isEqualTo(expected);
    }
}
