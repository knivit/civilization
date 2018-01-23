package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.unit.Warriors;
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
import com.tsoft.civilization.world.CivilizationsRelations;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class GetCityStatusTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeClass
    public static void classSetUp() {
        DefaultLogger.createLogger(GetCityStatusTest.class.getSimpleName());
        ajaxRequest = AbstractAjaxRequest.getInstance("GetCityStatus");
    }

    @Test
    public void getJSONForMyCity() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization c1 = new Civilization(mockWorld, 0);
        City city1 = new City(c1, new Point(2, 0));
        Civilization c2 = new Civilization(mockWorld, 1);
        mockWorld.setCivilizationsRelations(c1, c2, CivilizationsRelations.WAR);
        Warriors foreignWarriors = UnitFactory.newInstance(Warriors.INSTANCE, c2, new Point(2, 1));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        MockRequest request = MockRequest.newInstance();
        request.put("city", city1.getId());

        Response response = ajaxRequest.getJSON(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }

    @Test
    public void getJSONForMyDestroyedCity() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization c1 = new Civilization(mockWorld, 0);
        City city1 = new City(c1, new Point(2, 0));
        city1.getCombatStrength().setDestroyed(true);

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        MockRequest request = MockRequest.newInstance();
        request.put("city", city1.getId());

        Response response = ajaxRequest.getJSON(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }

    @Test
    public void getJSONForForeignCity() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization c1 = new Civilization(mockWorld, 0);
        City city1 = new City(c1, new Point(2, 0));

        Civilization c2 = new Civilization(mockWorld, 1);
        City city2 = new City(c2, new Point(2, 2));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        MockRequest mockRequest = MockRequest.newInstance();
        mockRequest.put("city", city2.getId());

        Response response = ajaxRequest.getJSON(mockRequest);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }
}
