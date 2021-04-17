package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.ajax.action.status.GetMyCities;
import com.tsoft.civilization.web.render.HtmlRender;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetMyCitiesTest {

    private static final AbstractAjaxRequest getMyCitiesRequest =
        AbstractAjaxRequest.getInstance(GetMyCities.class.getSimpleName());

    @Test
    public void getJson() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city", new Point(2, 0))
        );

        Sessions.getCurrent().setActiveCivilization(russia);
        Request request = MockRequest.newInstance();

        Response response = getMyCitiesRequest.getJson(request);
        HtmlRender.of(this).saveToFile(response);

        assertEquals(ResponseCode.OK, response.getResponseCode());
    }
}
