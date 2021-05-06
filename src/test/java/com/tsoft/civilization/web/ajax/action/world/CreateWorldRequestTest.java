package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.world.Climate;
import com.tsoft.civilization.world.DifficultyLevel;
import com.tsoft.civilization.world.MapConfiguration;
import com.tsoft.civilization.world.MapSize;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateWorldRequestTest {

    @Test
    public void getJson() {
        Request request = MockRequest.newInstance(
            "worldName", UUID.randomUUID().toString(),
            "mapConfiguration", MapConfiguration.AMAZON.name(),
            "mapSize", MapSize.SMALL.name(),
            "climate", Climate.NORMAL.name(),
            "maxNumberOfCivilizations", "2",
            "difficultyLevel", DifficultyLevel.PRINCE.name()
        );

        CreateWorldRequest createWorldRequest = new CreateWorldRequest();
        Response response = createWorldRequest.getJson(request);

        assertThat(response)
            .isNotNull()
            .returns(ResponseCode.OK, Response::getResponseCode)
            .returns(ContentType.TEXT_HTML, Response::getContentType);
    }
}
