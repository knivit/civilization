package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.ajax.action.status.GetMyUnits;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetMyUnitsTest {
    private static final AbstractAjaxRequest getMyUnitsRequest =
        AbstractAjaxRequest.getInstance(GetMyUnits.class.getSimpleName());

    @Test
    public void getJson() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization c1 = world.createCivilization(RUSSIA);
        Archers archers = UnitFactory.newInstance(c1, Archers.CLASS_UUID);
        assertTrue(c1.units().addUnit(archers, new Point(2, 0)));
        Workers workers = UnitFactory.newInstance(c1, Workers.CLASS_UUID);
        assertTrue(c1.units().addUnit(workers, new Point(2, 0)));

        Sessions.setActiveCivilization(c1);
        Request request = MockRequest.newInstance();

        Response response = getMyUnitsRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }
}
