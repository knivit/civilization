package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.ajax.action.status.GetTileStatus;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.L10n.L10nCivilization.AMERICA;
import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTileStatusTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeAll
    public static void classSetUp() {
        ajaxRequest = AbstractAjaxRequest.getInstance(GetTileStatus.class.getSimpleName());
    }

    @Test
    public void getJSONForMyCityAndUnit() {
        MockWorld world = MockWorld.newWorldWithFeatures();
        Civilization c1 = world.createCivilization(RUSSIA);
        City city1 = c1.createCity(new Point(2, 0));
        Workers workers1 = UnitFactory.newInstance(Workers.CLASS_UUID);
        c1.units().addUnit(workers1, city1.getLocation());

        Worlds.add(world);
        Sessions.setActiveCivilization(c1);
        Request request = MockRequest.newInstance("col", "2", "row", "0");

        Response response = ajaxRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }

    @Test
    public void getJSONForForeignCityAndUnit() {
        MockWorld world = MockWorld.newWorldWithFeatures();
        Civilization c1 = world.createCivilization(RUSSIA);
        Civilization c2 = world.createCivilization(AMERICA);
        City city1 = c1.createCity(new Point(2, 0));
        Workers workers1 = UnitFactory.newInstance(Workers.CLASS_UUID);
        c1.units().addUnit(workers1, city1.getLocation());

        Worlds.add(world);
        Sessions.setActiveCivilization(c2);
        Request request = MockRequest.newInstance("col", "2", "row", "0");

        Response response = ajaxRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }
}
