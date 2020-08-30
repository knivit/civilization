package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.unit.military.Warriors;
import com.tsoft.civilization.unit.civil.Workers;
import com.tsoft.civilization.unit.util.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.Civilization;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetCivilizationStatusTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeAll
    public static void classSetUp() {
        ajaxRequest = AbstractAjaxRequest.getInstance("GetCivilizationStatus");
    }

    @Test
    public void getJSONForMyCivilization() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization c1 = new Civilization(mockWorld, 0);
        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID, c1, new Point(2, 0));
        Warriors warriors = UnitFactory.newInstance(Warriors.CLASS_UUID, c1, new Point(2, 1));
        City city1 = new City(c1, new Point(2, 2));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        MockRequest mockRequest = MockRequest.newInstance();
        mockRequest.put("civilization", c1.getId());

        Response response = ajaxRequest.getJSON(mockRequest);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }

    @Test
    public void getJSONForForeignCivilization() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization c1 = new Civilization(mockWorld, 0);
        Civilization c2 = new Civilization(mockWorld, 1);
        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID, c2, new Point(2, 0));
        Warriors warriors = UnitFactory.newInstance(Warriors.CLASS_UUID, c2, new Point(2, 1));
        City city1 = new City(c2, new Point(2, 2));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        MockRequest mockRequest = MockRequest.newInstance();
        mockRequest.put("civilization", c1.getId());

        Response response = ajaxRequest.getJSON(mockRequest);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }
}
