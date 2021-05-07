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
import com.tsoft.civilization.civilization.CivilizationsRelations;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetCityStatusTest {
    private static final AbstractAjaxRequest getCityStatusRequest =
        AbstractAjaxRequest.getInstance(GetCityStatus.class.getSimpleName());

    @Test
    public void get_json_for_my_city() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(2, 0))
        );

        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWarriors", new Point(2, 1))
        );

        world.startGame();
        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        Sessions.getCurrent().setActiveCivilization(russia);
        Request request = MockRequest.newInstance("city", world.city("city1").getId());

        Response response = getCityStatusRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }

    @Test
    public void get_json_for_my_destroyed_city() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(2, 0))
        );

        world.startGame();
        Sessions.getCurrent().setActiveCivilization(russia);

        world.city("city1").destroy();

        Request request = MockRequest.newInstance("city", world.city("city1").getId());

        Response response = getCityStatusRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }

    @Test
    public void get_json_for_foreign_city() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(2, 0))
        );

        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .city("city2", new Point(2, 2))
        );

        world.startGame();
        Sessions.getCurrent().setActiveCivilization(russia);
        Request mockRequest = MockRequest.newInstance("city", world.city("city2").getId());

        Response response = getCityStatusRequest.getJson(mockRequest);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }
}
