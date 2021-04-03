package com.tsoft.civilization.web;

import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.state.Sessions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostRequestProcessor {
    public static final String REQUEST_PREFIX = "ajax/";

    private PostRequestProcessor() { }

    public static Response processRequest(Request request) {
        String ajaxClassName = request.getRequestUrl();
        AbstractAjaxRequest ajaxRequest = null;

        if (ajaxClassName.startsWith(REQUEST_PREFIX)) {
            String requestName = ajaxClassName.substring(REQUEST_PREFIX.length());
            ajaxRequest = AbstractAjaxRequest.getInstance(requestName);
        }

        if (ajaxRequest == null) {
            log.warn("Unknown request {}", ajaxClassName);
            return JsonResponse.badRequest(L10nServer.ACTION_NOT_FOUND);
        }

        if (Sessions.getCurrent() == null) {
            return JsonResponse.badRequest(L10nServer.INVALID_SESSION);
        }

        return ajaxRequest.getJson(request);
    }
}
