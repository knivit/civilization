package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
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
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class GetCivilizationStatusTest {
    private static final AbstractAjaxRequest getCivilizationStatusRequest =
        AbstractAjaxRequest.getInstance(GetCivilizationStatus.class.getSimpleName());

    @Test
    public void getJsonForMyCivilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .workers("workers", new Point(2, 0))
            .warriors("warriors", new Point(2, 1))
            .city("city1", new Point(2, 2))
        );

        world.startGame();
        Sessions.getCurrent().setActiveCivilization(russia);
        Request request = MockRequest.newInstance("civilization", russia.getId());

        Response response = getCivilizationStatusRequest.getJson(request);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.OK);
    }

    @Test
    public void getJsonForForeignCivilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario());

        world.createCivilization(AMERICA, new MockScenario()
            .workers("workers", new Point(2, 0))
            .warriors("warriors", new Point(2, 1))
            .city("city1", new Point(2, 2))
        );

        world.startGame();
        Sessions.getCurrent().setActiveCivilization(russia);
        Request mockRequest = MockRequest.newInstance("civilization", russia.getId());

        Response response = getCivilizationStatusRequest.getJson(mockRequest);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.OK);
    }
}
