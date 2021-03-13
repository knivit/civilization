package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.improvement.city.City;
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

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetBuildingStatusTest {
    private static final AbstractAjaxRequest getBuildingStatusRequest =
        AbstractAjaxRequest.getInstance(GetBuildingStatus.class.getSimpleName());

    @Test
    public void getJsonForMyCityBuilding() {
        MockWorld world = MockWorld.newSimpleWorld();
        Worlds.add(world);

        Civilization c1 = world.createCivilization(RUSSIA);
        City city1 = c1.createCity(new Point(2, 0));

        Sessions.setActiveCivilization(c1);
        Request request = MockRequest.newInstance("building", city1.getBuildings().findByClassUuid(Palace.CLASS_UUID).getId());

        Response response = getBuildingStatusRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }

    @Test
    public void getJsonForForeignCityBuilding() {
        MockWorld world = MockWorld.newSimpleWorld();
        Worlds.add(world);

        Civilization c1 = world.createCivilization(RUSSIA);
        Civilization c2 = world.createCivilization(AMERICA);
        City city2 = c2.createCity(new Point(2, 0));

        Sessions.setActiveCivilization(c1);
        Request request = MockRequest.newInstance("building", city2.getBuildings().findByClassUuid(Palace.CLASS_UUID).getId());

        Response response = getBuildingStatusRequest.getJson(request);
        assertEquals(ResponseCode.BAD_REQUEST, response.getResponseCode());
    }
}
