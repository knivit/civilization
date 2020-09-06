package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;

import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetCivilizationsTest {
    private static AbstractAjaxRequest ajaxRequest;

    @BeforeAll
    public static void classSetUp() {
        ajaxRequest = AbstractAjaxRequest.getInstance("GetCivilizations");
    }

    @Test
    public void getJSON() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization c1 = new Civilization(mockWorld, 0);
        Civilization c2 = new Civilization(mockWorld, 1);

        ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        Worlds.add(mockWorld);
        session.setWorldAndCivilizationIds(c1);

        Request request = MockRequest.newInstance();

        Response response = ajaxRequest.getJSON(request);
        assertEquals(ResponseCode.OK, response.getErrorCode());
    }
}
