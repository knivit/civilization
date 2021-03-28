package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.state.Sessions;

public class SelectLanguageRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        String language = request.get("language");
        Sessions.getCurrent().setLanguage(language);

        return HtmlResponse.ok(new StringBuilder());
    }
}
