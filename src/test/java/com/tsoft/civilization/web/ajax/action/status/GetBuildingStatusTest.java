package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetBuildingStatusTest {
    private static final AbstractAjaxRequest getBuildingStatusRequest =
        AbstractAjaxRequest.getInstance(GetBuildingStatus.class.getSimpleName());

    @Test
    public void get_json_for_my_city_building() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Tula", new Point(2, 0))
        );

        world.startGame();
        Sessions.getCurrent().setActiveCivilization(russia);

        String palaceId = world.city("Tula").getBuildings().findByClassUuid(Palace.CLASS_UUID).getId();
        Request request = MockRequest.newInstance("building", palaceId);

        Response response = getBuildingStatusRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }

    @Test
    public void getJsonForForeignCityBuilding() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario());

        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .city("Washington", new Point(2, 0))
        );

        world.startGame();
        Sessions.getCurrent().setActiveCivilization(russia);

        String palaceId = world.city("Washington").getBuildings().findByClassUuid(Palace.CLASS_UUID).getId();
        Request request = MockRequest.newInstance("building", palaceId);

        Response response = getBuildingStatusRequest.getJson(request);
        assertEquals(ResponseCode.BAD_REQUEST, response.getResponseCode());
    }
}
