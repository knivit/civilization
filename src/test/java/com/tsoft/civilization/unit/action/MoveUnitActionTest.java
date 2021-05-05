package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.helper.html.HtmlAttribute;
import com.tsoft.civilization.helper.html.HtmlDocument;
import com.tsoft.civilization.helper.html.HtmlParser;
import com.tsoft.civilization.helper.html.HtmlTag;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.move.UnitMoveService;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.move.UnitMoveService.CAN_MOVE;
import static org.assertj.core.api.Assertions.assertThat;
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
        HtmlDocument doc = HtmlParser.parse(buf);
        assertThat(doc).isNotNull()
            .returns(2, e -> e.getTags().size())
            .extracting(e -> e.getTags().get(0))
            .returns("td", HtmlTag::getName)
            .returns(null, HtmlTag::getText)
            .returns(Collections.emptyList(), HtmlTag::getAttributes)
            .returns(1, e -> e.getTags().size())
            .extracting(e -> e.getTags().get(0))
            .returns("button", HtmlTag::getName)
            .returns("Move", HtmlTag::getText)
            .returns(Collections.emptyList(), HtmlTag::getTags)
            .returns(1, e -> e.getAttributes().size())
            .extracting(e -> e.getAttributes().get(0))
            .returns("onclick", HtmlAttribute::getName)
            .returns("client.moveUnitAction({ unit:'" + workers.getId() + "', ucol:'1', urow:'1', 'locations':[{'col':'2','row':'0'},{'col':'2','row':'1'}] })", HtmlAttribute::getValue);
    }
}
