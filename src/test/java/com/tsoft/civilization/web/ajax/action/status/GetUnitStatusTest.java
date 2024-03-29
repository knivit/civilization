package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class GetUnitStatusTest {

    private static final AbstractAjaxRequest getUnitStatusRequest =
        AbstractAjaxRequest.getInstance(GetUnitStatus.class.getSimpleName());

    @Test
    public void getJsonForMyUnit() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization c1 = world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0))
        );

        Sessions.getCurrent().setActiveCivilization(c1);
        Request request = MockRequest.newInstance("unit", world.unit("warriors").getId());

        Response response = getUnitStatusRequest.getJson(request);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.OK);
    }
}
