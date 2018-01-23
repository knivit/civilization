package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.unit.Workers;
import com.tsoft.civilization.unit.util.UnitFactory;
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.Civilization;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class GetTileStatusTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeClass
    public static void classSetUp() {
        DefaultLogger.createLogger(GetTileStatusTest.class.getSimpleName());
        ajaxRequest = AbstractAjaxRequest.getInstance("GetTileStatus");
    }

    @Test
    public void getJSONForMyCityAndUnit() {
        MockWorld mockWorld = MockWorld.newWorldWithFeatures();
        Civilization c1 = new Civilization(mockWorld, 0);
        City city1 = new City(c1, new Point(2, 0));
        Workers workers1 = UnitFactory.newInstance(Workers.INSTANCE, c1, city1.getLocation());

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        MockRequest request = MockRequest.newInstance();
        request.put("col", "2");
        request.put("row", "0");

        Response response = ajaxRequest.getJSON(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }

    @Test
    public void getJSONForForeignCityAndUnit() {
        MockWorld mockWorld = MockWorld.newWorldWithFeatures();
        Civilization c1 = new Civilization(mockWorld, 0);
        Civilization c2 = new Civilization(mockWorld, 1);
        City city1 = new City(c1, new Point(2, 0));
        Workers workers1 = UnitFactory.newInstance(Workers.INSTANCE, c1, city1.getLocation());

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c2);

        MockRequest request = MockRequest.newInstance();
        request.put("col", "2");
        request.put("row", "0");

        Response response = ajaxRequest.getJSON(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }
}
