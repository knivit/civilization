package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTileStatusTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeAll
    public static void classSetUp() {
        ajaxRequest = AbstractAjaxRequest.getInstance("GetTileStatus");
    }

    @Test
    public void getJSONForMyCityAndUnit() {
        MockWorld mockWorld = MockWorld.newWorldWithFeatures();
        Civilization c1 = new Civilization(mockWorld, 0);
        City city1 = new City(c1, new Point(2, 0));
        Workers workers1 = UnitFactory.newInstance(Workers.CLASS_UUID);
        c1.addUnit(workers1, city1.getLocation());

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        Request request = MockRequest.newInstance("col", "2", "row", "0");

        Response response = ajaxRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }

    @Test
    public void getJSONForForeignCityAndUnit() {
        MockWorld mockWorld = MockWorld.newWorldWithFeatures();
        Civilization c1 = new Civilization(mockWorld, 0);
        Civilization c2 = new Civilization(mockWorld, 1);
        City city1 = new City(c1, new Point(2, 0));
        Workers workers1 = UnitFactory.newInstance(Workers.CLASS_UUID);
        c1.addUnit(workers1, city1.getLocation());

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c2);

        Request request = MockRequest.newInstance("col", "2", "row", "0");

        Response response = ajaxRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }
}
