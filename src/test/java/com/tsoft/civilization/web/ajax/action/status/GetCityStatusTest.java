package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.L10n.L10nCivilization.AMERICA;
import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetCityStatusTest {
    private static final AbstractAjaxRequest getCityStatusRequest =
        AbstractAjaxRequest.getInstance(GetCityStatus.class.getSimpleName());

    @Test
    public void getJsonForMyCity() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization c1 = world.createCivilization(RUSSIA);
        City city1 = c1.createCity(new Point(2, 0));

        Civilization c2 = world.createCivilization(AMERICA);
        world.setCivilizationsRelations(c1, c2, CivilizationsRelations.WAR);
        Warriors foreignWarriors = UnitFactory.newInstance(c2, Warriors.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignWarriors, new Point(2, 1)));

        Worlds.add(world);
        Sessions.setActiveCivilization(c1);
        Request request = MockRequest.newInstance("city", city1.getId());

        Response response = getCityStatusRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }

    @Test
    public void getJsonForMyDestroyedCity() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization c1 = world.createCivilization(RUSSIA);
        City city1 = c1.createCity(new Point(2, 0));
        city1.getCombatStrength().setDestroyed(true);

        Worlds.add(world);
        Sessions.setActiveCivilization(c1);
        Request request = MockRequest.newInstance("city", city1.getId());

        Response response = getCityStatusRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }

    @Test
    public void getJsonForForeignCity() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization c1 = world.createCivilization(RUSSIA);
        City city1 = c1.createCity(new Point(2, 0));

        Civilization c2 = world.createCivilization(AMERICA);
        City city2 = c2.createCity(new Point(2, 2));

        Worlds.add(world);
        Sessions.setActiveCivilization(c1);
        Request mockRequest = MockRequest.newInstance("city", city2.getId());

        Response response = getCityStatusRequest.getJson(mockRequest);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }
}
