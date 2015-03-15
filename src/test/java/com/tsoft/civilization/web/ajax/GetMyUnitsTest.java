package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.Archers;
import com.tsoft.civilization.unit.Workers;
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.Civilization;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class GetMyUnitsTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeClass
    public static void classSetUp() {
        DefaultLogger.createLogger(GetMyUnitsTest.class.getSimpleName());
        ajaxRequest = AbstractAjaxRequest.getInstance("GetMyUnits");
    }

    @Test
    public void getJSON() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization c1 = new Civilization(mockWorld, 0);
        Archers archers = AbstractUnit.newInstance(Archers.INSTANCE, c1, new Point(2, 0));
        Workers workers = AbstractUnit.newInstance(Workers.INSTANCE, c1, new Point(2, 0));

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        MockRequest request = MockRequest.newInstance();

        Response response = ajaxRequest.getJSON(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }
}
