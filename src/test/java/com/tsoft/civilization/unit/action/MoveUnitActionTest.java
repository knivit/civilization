package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.helper.html.HtmlDocument;
import com.tsoft.civilization.helper.html.HtmlParser;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.service.move.MoveUnitService;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.service.move.MoveUnitService.CAN_MOVE;
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

        world.startGame();

        AbstractUnit workers = world.unit("workers");
        Set<Point> locations = new LinkedHashSet<>(List.of(new Point(2, 1), new Point(2, 0)));

        MoveUnitService moveUnitService = mock(MoveUnitService.class);
        when(moveUnitService.checkCanMove(any())).thenReturn(CAN_MOVE);
        when(moveUnitService.getLocationsToMove(any())).thenReturn(locations);

        MoveUnitAction moveUnitAction = new MoveUnitAction(moveUnitService);
        StringBuilder buf = moveUnitAction.getHtml(workers);

        HtmlDocument expected = HtmlParser.parse(Format.text("""
            <td>
              <button onclick="client.moveUnitAction({ unit:'$workersId', col:'1', row:'1', 'locations':[{'col':'2','row':'1'},{'col':'2','row':'0'}] })">Move</button>
            </td>
            <td>Move unit to a tile</td>
            """,

            "$workersId", workers.getId()));

        HtmlDocument actual = HtmlParser.parse(buf);
        assertThat(actual).isEqualTo(expected);
    }
}
