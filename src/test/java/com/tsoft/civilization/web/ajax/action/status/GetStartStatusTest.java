package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetStartStatusTest {

    private static final AbstractAjaxRequest getStartStatusRequest =
        AbstractAjaxRequest.getInstance(GetStartStatus.class.getSimpleName());

    @Test
    public void getJson() {
        Request request = MockRequest.newInstance();
        Response response = getStartStatusRequest.getJson(request);
        assertEquals(ResponseCode.OK, response.getResponseCode());
    }
}
