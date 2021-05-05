package com.tsoft.civilization.web.ajax.action.city;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildBuildingActionRequestTest {

    private final BuildBuildingActionRequest buildBuildingActionRequest = new BuildBuildingActionRequest();

    @Test
    public void get_json() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Tula", new Point(2, 0))
        );

        Sessions.getCurrent().setActiveCivilization(russia);

        String palaceId = world.city("Tula").getBuildings().findByClassUuid(Palace.CLASS_UUID).getId();
        Request request = MockRequest.newInstance("building", palaceId);

        Response response = buildBuildingActionRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }
}
