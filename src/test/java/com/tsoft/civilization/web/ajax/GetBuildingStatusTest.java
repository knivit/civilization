package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetBuildingStatusTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeAll
    public static void classSetUp() {
        ajaxRequest = AbstractAjaxRequest.getInstance("GetBuildingStatus");
    }

    @Test
    public void getJSONForMyCityBuilding() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization c1 = new Civilization(mockWorld, 0);
        City city1 = new City(c1, new Point(2, 0));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        MockRequest request = MockRequest.newInstance();
        request.put("building", city1.getBuildings().findByClassUuid(Palace.CLASS_UUID).getId());

        Response response = ajaxRequest.getJSON(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }

    @Test
    public void getJSONForForeignCityBuilding() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization c1 = new Civilization(mockWorld, 0);

        Civilization c2 = new Civilization(mockWorld, 1);
        City city2 = new City(c2, new Point(2, 0));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        MockRequest request = MockRequest.newInstance();
        request.put("building", city2.getBuildings().findByClassUuid(Palace.CLASS_UUID).getId());

        Response response = ajaxRequest.getJSON(request);
        assertEquals(ResponseCode.BAD_REQUEST, response.getErrorCode());
    }
}
