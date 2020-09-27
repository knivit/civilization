package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.military.warriors.Warriors;
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
import com.tsoft.civilization.civilization.CivilizationsRelations;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.tsoft.civilization.L10n.L10nCivilization.AMERICA;
import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetCityStatusTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeAll
    public static void classSetUp() {
        ajaxRequest = AbstractAjaxRequest.getInstance("GetCityStatus");
    }

    @Test
    public void getJSONForMyCity() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization c1 = world.createCivilization(RUSSIA);
        City city1 = new City(c1, new Point(2, 0));

        Civilization c2 = world.createCivilization(AMERICA);
        world.setCivilizationsRelations(c1, c2, CivilizationsRelations.WAR);
        Warriors foreignWarriors = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c2.units().addUnit(foreignWarriors, new Point(2, 1));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(world);
        session.setWorldAndCivilizationIds(c1);

        Request request = MockRequest.newInstance("city", city1.getId());

        Response response = ajaxRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }

    @Test
    public void getJSONForMyDestroyedCity() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization c1 = world.createCivilization(RUSSIA);
        City city1 = new City(c1, new Point(2, 0));
        city1.getCombatStrength().setDestroyed(true);

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(world);
        session.setWorldAndCivilizationIds(c1);

        Request request = MockRequest.newInstance("city", city1.getId());

        Response response = ajaxRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }

    @Test
    public void getJSONForForeignCity() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization c1 = world.createCivilization(RUSSIA);
        City city1 = new City(c1, new Point(2, 0));

        Civilization c2 = world.createCivilization(AMERICA);
        City city2 = new City(c2, new Point(2, 2));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(world);
        session.setWorldAndCivilizationIds(c1);

        Request mockRequest = MockRequest.newInstance("city", city2.getId());

        Response response = ajaxRequest.getJson(mockRequest);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }
}
