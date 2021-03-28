package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateWorldRequestTest {

    private static final String WORLD_NAME = UUID.randomUUID().toString();

    @Test
    public void getJson() {
        Request request = MockRequest.newInstance(
            "worldName", WORLD_NAME,
            "mapWidth", "10",
            "mapHeight", "4",
            "worldType", "0",
            "climate", "0",
            "maxNumberOfCivilizations", "2"
        );

        CreateWorldRequest createWorldRequest = new CreateWorldRequest();
        Response response = createWorldRequest.getJson(request);

        assertThat(response)
            .isNotNull()
            .returns(ResponseCode.OK, Response::getResponseCode)
            .returns(ContentType.TEXT_HTML, Response::getContentType);
    }
}
