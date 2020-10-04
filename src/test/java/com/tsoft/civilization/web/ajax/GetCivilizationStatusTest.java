package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.military.warriors.Warriors;
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

import static com.tsoft.civilization.L10n.L10nCivilization.AMERICA;
import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetCivilizationStatusTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeAll
    public static void classSetUp() {
        ajaxRequest = AbstractAjaxRequest.getInstance("GetCivilizationStatus");
    }

    @Test
    public void getJSONForMyCivilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization c1 = world.createCivilization(RUSSIA);
        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID);
        c1.units().addUnit(workers, new Point(2, 0));
        Warriors warriors = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c1.units().addUnit(warriors, new Point(2, 1));
        City city1 = c1.createCity(new Point(2, 2));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(world);
        session.setActiveCivilization(c1);

        Request request = MockRequest.newInstance("civilization", c1.getId());

        Response response = ajaxRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }

    @Test
    public void getJSONForForeignCivilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization c1 = world.createCivilization(RUSSIA);

        Civilization c2 = world.createCivilization(AMERICA);
        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID);
        c2.units().addUnit(workers, new Point(2, 0));
        Warriors warriors = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c2.units().addUnit(warriors, new Point(2, 1));
        City city1 = c2.createCity(new Point(2, 2));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(world);
        session.setActiveCivilization(c1);

        Request mockRequest = MockRequest.newInstance("civilization", c1.getId());

        Response response = ajaxRequest.getJson(mockRequest);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }
}
