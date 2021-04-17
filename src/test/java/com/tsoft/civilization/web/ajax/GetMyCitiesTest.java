package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.ajax.action.status.GetMyCities;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetMyCitiesTest {
    private static final AbstractAjaxRequest getMyCitiesRequest =
        AbstractAjaxRequest.getInstance(GetMyCities.class.getSimpleName());

    @Test
    public void getJson() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization c1 = world.createCivilization(RUSSIA);

        Settlers settlers = (Settlers)c1.units().findByClassUuid(Settlers.CLASS_UUID).getAny();
        assertThat(settlers).isNotNull();
        City city1 = c1.createCity(settlers);

        Sessions.getCurrent().setActiveCivilization(c1);
        Request request = MockRequest.newInstance();

        Response response = getMyCitiesRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }
}
