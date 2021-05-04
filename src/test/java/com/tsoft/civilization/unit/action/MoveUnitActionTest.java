package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.move.UnitMoveService;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.move.UnitMoveService.CAN_MOVE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoveUnitActionTest {

    @Test
    public void get_html() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .workers("workers", new Point(1, 1)));

        AbstractUnit workers = world.unit("workers");
        Set<Point> locations = Set.of(new Point(2, 1), new Point(2, 0));

        UnitMoveService unitMoveService = mock(UnitMoveService.class);
        when(unitMoveService.canMove(any())).thenReturn(CAN_MOVE);
        when(unitMoveService.getLocationsToMove(any())).thenReturn(locations);

        MoveUnitAction moveUnitAction = new MoveUnitAction(unitMoveService);

        StringBuilder buf = moveUnitAction.getHtml(workers);

        // <td><button onclick="client.moveUnitAction({
        //     unit:'149650c5-9dad-4800-bc49-ed0e7c261bcd',
        //     ucol:'1',
        //     urow:'1',
        //     'locations':[{'col':'2','row':'1'},{'col':'2','row':'0'}] })">Move</button>
        // </td>
        // <td>Move unit to a tile</td>
/*

 todo see HtmlHelper

 Document doc = Jsoup.parse(buf.toString());
        assertThat(doc.select("button"))
            .hasSize(1)
            .first()
            .returns("Move", Element::text)
            .extracting(e -> e.attr("onclick"))
            .extracting(HtmlHelper::extractEvent)
            .returns("client.moveUnitAction", MockHtmlEvent::getAction)
            .extracting(MockHtmlEvent::getArgs)
            .isNotNull()
            .returns(workers.getId(), e -> e.get("unit").asText())
            .returns(workers.getLocation(), e -> new Point(e.get("ucol").asInt(), e.get("urow").asInt()))
            .returns(locations, e -> HtmlHelper.extractLocations(e.get("locations")));
*/    }
}
