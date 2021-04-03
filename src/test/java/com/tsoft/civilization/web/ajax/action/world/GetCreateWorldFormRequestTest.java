package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCreateWorldFormRequestTest {

    @Test
    public void getJson() {
        Request request = MockRequest.newInstance();

        GetCreateWorldFormRequest getCreateWorldFormRequest = new GetCreateWorldFormRequest();

        Response response = getCreateWorldFormRequest.getJson(request);
        assertThat(response)
            .isNotNull()
            .returns(ResponseCode.OK, Response::getResponseCode)
            .returns(ContentType.TEXT_HTML, Response::getContentType);
    }
}
