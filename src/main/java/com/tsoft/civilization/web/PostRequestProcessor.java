package com.tsoft.civilization.web;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.state.Sessions;

public class PostRequestProcessor {
    private PostRequestProcessor() { }

    public static Response processRequest(Request request) {
        String ajaxClassName = request.getRequestUrl();
        AbstractAjaxRequest ajaxRequest = null;
        if (ajaxClassName.startsWith("ajax/")) {
            ajaxRequest = AbstractAjaxRequest.getInstance(ajaxClassName.substring(5));
        }

        if (ajaxRequest == null) {
            return Response.newErrorInstance(L10nServer.ACTION_NOT_FOUND);
        }

        if (Sessions.getCurrent() == null) {
            return Response.newErrorInstance(L10nServer.INVALID_SESSION);
        }

        return ajaxRequest.getJson(request);
    }
}
