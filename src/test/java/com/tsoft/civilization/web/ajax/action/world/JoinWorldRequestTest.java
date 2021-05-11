package com.tsoft.civilization.web.ajax.action.world;

import com.fasterxml.jackson.databind.JsonNode;
import com.tsoft.civilization.helper.JsonHelper;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.ajax.RequestsMap;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.MockWorld.GRASSLAND_MAP_10x10;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class JoinWorldRequestTest {

    @Test
    public void getJson() {
        MockWorld world = MockWorld.of(GRASSLAND_MAP_10x10);

        Request request = MockRequest.newInstance(
            "world", world.getId(),
            "civilization", RUSSIA.getEnglish(),
            "playerType", PlayerType.HUMAN.name()
        );

        Response response = RequestsMap.get(JoinWorldRequest.class.getSimpleName()).getJson(request);

        assertThat(response).isNotNull()
            .returns(ResponseCode.OK, Response::getResponseCode)
            .returns(ContentType.APPLICATION_JSON, Response::getContentType);

        Civilization civilization = Sessions.getCurrent().getCivilization();
        assertThat(civilization).isNotNull();

        UnitList units = civilization.getUnitService().findByClassUuid(Settlers.CLASS_UUID);
        assertThat(units).isNotNull().hasSize(1);

        Settlers settlers = (Settlers)units.getAny();

        JsonNode json = JsonHelper.parse(response);

        assertThat(json.get("col").asInt())
            .isEqualTo(settlers.getLocation().getX());

        assertThat(json.get("row").asInt())
            .isEqualTo(settlers.getLocation().getY());
    }
}
