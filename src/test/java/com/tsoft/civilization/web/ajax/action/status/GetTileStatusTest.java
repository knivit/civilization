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

public class GetTileStatusTest {
    private static final AbstractAjaxRequest getTileStatusRequest =
        AbstractAjaxRequest.getInstance(GetTileStatus.class.getSimpleName());

    @Test
    public void getJsonForMyCityAndUnit() {
        MockWorld world = MockWorld.newWorldWithFeatures();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(2, 0))
            .workers("workers1", new Point(2, 0))
        );

        Sessions.getCurrent().setActiveCivilization(russia);
        Request request = MockRequest.newInstance("col", "2", "row", "0");

        Response response = getTileStatusRequest.getJson(request);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.OK);
    }

    @Test
    public void getJsonForForeignCityAndUnit() {
        MockWorld world = MockWorld.newWorldWithFeatures();

        world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(2, 0))
            .workers("workers1", new Point(2, 0))
        );

        Civilization america = world.createCivilization(AMERICA, new MockScenario());

        Sessions.getCurrent().setActiveCivilization(america);
        Request request = MockRequest.newInstance("col", "2", "row", "0");

        Response response = getTileStatusRequest.getJson(request);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.OK);
    }
}
