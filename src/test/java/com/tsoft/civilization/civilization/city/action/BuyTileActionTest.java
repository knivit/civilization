package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.tile.BuyTile;
import com.tsoft.civilization.civilization.city.tile.BuyTileService;
import com.tsoft.civilization.helper.html.HtmlDocument;
import com.tsoft.civilization.helper.html.HtmlParser;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BuyTileActionTest {

    @Test
    void get_html() {
        BuyTileService buyTileService = mock(BuyTileService.class);
        BuyTileAction buyTileAction = new BuyTileAction(buyTileService);

        MockWorld world = MockWorld.newSimpleWorld();
        world.createCivilization(RUSSIA, new MockScenario()
            .city("city", new Point(2, 0)));

        City city = world.city("city");

        when(buyTileService.canBuyTile(eq(city))).thenReturn(CityTileActionResults.CAN_BUY_TILE);
        when(buyTileService.getLocationsToBuy(eq(city))).thenReturn(List.of(
            new BuyTile(new Point(1, 1), 100),
            new BuyTile(new Point(2, 1), 80)
        ));

        StringBuilder buf = buyTileAction.getHtml(city);
        HtmlDocument actual = HtmlParser.parse(buf);

        HtmlDocument expected = HtmlParser.parse(Format.text("""
            <td>
                <button onclick="client.buyTileAction({ city:'$cityId', 'locations':[{'col':'1','row':'1','price':'100'},{'col':'2','row':'1','price':'80'}] })">Buy a tile</button>
            </td>
            <td>Buy a tile to expand your city's territory</td>
            """,

            "$cityId", city.getId()));

        assertThat(actual).isEqualTo(expected);
    }

}