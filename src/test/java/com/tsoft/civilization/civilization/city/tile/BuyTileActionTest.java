package com.tsoft.civilization.civilization.city.tile;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.action.BuyTileAction;
import com.tsoft.civilization.civilization.city.ui.CityTileActionResults;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.economic.SupplyMock;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

class BuyTileActionTest {

    private static final BuyTileAction BUY_TILE_ACTION = new BuyTileAction();

    @Test
    void can_buy_tile() {
        MockWorld world = MockWorld.newSimpleWorld();
        world.createCivilization(RUSSIA, new MockScenario()
            .city("city", Point.of(1, 1)));

        City city = world.city("city");

        assertThat(city.getSupplyService().getSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F0 P0 G0"));

        List<Point> cityTerritory = new ArrayList<>(List.of(
            Point.of(1, 0), Point.of(2, 0),
            Point.of(0, 1), Point.of(1, 1), Point.of(2, 1),
            Point.of(1, 2), Point.of(2, 2)
        ));

        assertThat(city.getTileService().getTerritory()).containsExactlyInAnyOrderElementsOf(cityTerritory);
        assertThat(BUY_TILE_ACTION.canBuyTile(city)).isEqualTo(CityTileActionResults.CAN_BUY_TILE);

        List<TileCost> expectedTiles = List.of(
            new TileCost(new Point(0, 0), 60),
            new TileCost(new Point(0, 2), 60),
            new TileCost(new Point(3, 0), 60),
            new TileCost(new Point(3, 2), 60),
            new TileCost(new Point(0, 3), 60),
            new TileCost(new Point(1, 3), 60),
            new TileCost(new Point(3, 1), 60),
            new TileCost(new Point(2, 3), 60),
            new TileCost(new Point(3, 3), 60));

        assertThat(BUY_TILE_ACTION.getLocationsToBuy(city)).containsExactlyInAnyOrderElementsOf(expectedTiles);

        assertThat(BUY_TILE_ACTION.buyTile(city, new Point(0, 0))).isEqualTo(CityTileActionResults.NOT_ENOUGH_GOLD);

        // add some gold to the city
        city.getSupplyService().addIncome(Supply.builder().gold(60).build());
        assertThat(city.getSupplyService().getSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F0 P0 G60"));

        // buy a tile
        Point locationToBuy = new Point(0, 0);
        assertThat(BUY_TILE_ACTION.buyTile(city, locationToBuy)).isEqualTo(CityTileActionResults.TILE_BOUGHT);

        cityTerritory.add(locationToBuy);
        assertThat(city.getTileService().getTerritory()).containsExactlyInAnyOrderElementsOf(cityTerritory);

        assertThat(city.getSupplyService().getSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F0 P0 G0"));
    }
}