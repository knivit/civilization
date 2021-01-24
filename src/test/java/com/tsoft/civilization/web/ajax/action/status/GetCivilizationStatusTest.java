package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.unit.civil.workers.Workers;
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
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.L10n.L10nCivilization.AMERICA;
import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetCivilizationStatusTest {
    private static AbstractAjaxRequest getCivilizationStatusRequest =
        AbstractAjaxRequest.getInstance(GetCivilizationStatus.class.getSimpleName());

    @Test
    public void getJSONForMyCivilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization c1 = world.createCivilization(RUSSIA);
        Workers workers = UnitFactory.newInstance(c1, Workers.CLASS_UUID);
        assertTrue(c1.units().addUnit(workers, new Point(2, 0)));
        Warriors warriors = UnitFactory.newInstance(c1, Warriors.CLASS_UUID);
        assertTrue(c1.units().addUnit(warriors, new Point(2, 1)));
        City city1 = c1.createCity(new Point(2, 2));

        Worlds.add(world);
        Sessions.setActiveCivilization(c1);
        Request request = MockRequest.newInstance("civilization", c1.getId());

        Response response = getCivilizationStatusRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }

    @Test
    public void getJSONForForeignCivilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization c1 = world.createCivilization(RUSSIA);

        Civilization c2 = world.createCivilization(AMERICA);
        Workers workers = UnitFactory.newInstance(c2, Workers.CLASS_UUID);
        assertTrue(c2.units().addUnit(workers, new Point(2, 0)));
        Warriors warriors = UnitFactory.newInstance(c2, Warriors.CLASS_UUID);
        assertTrue(c2.units().addUnit(warriors, new Point(2, 1)));
        City city1 = c2.createCity(new Point(2, 2));

        Worlds.add(world);
        Sessions.setActiveCivilization(c1);
        Request mockRequest = MockRequest.newInstance("civilization", c1.getId());

        Response response = getCivilizationStatusRequest.getJson(mockRequest);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }
}
