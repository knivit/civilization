package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.unit.military.Archers;
import com.tsoft.civilization.unit.civil.Workers;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetMyUnitsTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeAll
    public static void classSetUp() {
        ajaxRequest = AbstractAjaxRequest.getInstance("GetMyUnits");
    }

    @Test
    public void getJSON() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization c1 = new Civilization(mockWorld, 0);
        Archers archers = UnitFactory.newInstance(Archers.CLASS_UUID);
        c1.addUnit(archers, new Point(2, 0));
        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID);
        c1.addUnit(workers, new Point(2, 0));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        MockRequest request = MockRequest.newInstance();

        Response response = ajaxRequest.getJSON(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }
}
