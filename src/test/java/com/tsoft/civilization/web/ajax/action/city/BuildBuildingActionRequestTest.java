package com.tsoft.civilization.web.ajax.action.city;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.building.monument.Monument;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class BuildBuildingActionRequestTest {

    private final BuildBuildingActionRequest buildBuildingActionRequest = new BuildBuildingActionRequest();

    @Test
    public void get_json() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city", new Point(2, 0))
        );
        City city = world.city("city");

        Sessions.getCurrent().setActiveCivilization(russia);

        Request request = MockRequest.newInstance(
            "city", city.getId(),
            "buildingUuid", Monument.CLASS_UUID);

        Response response = buildBuildingActionRequest.getJson(request);

        assertThat(response)
            .returns(ResponseCode.OK, Response::getResponseCode);
    }
}
