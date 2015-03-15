package com.tsoft.civilization.web;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.util.Response;

public class PostRequestProcessor {
    private PostRequestProcessor() { }

    public static void processRequest(ServerClient client) {
        String ajaxClassName = client.getRequest().getRequestUrl();
        AbstractAjaxRequest ajaxRequest = null;
        if (ajaxClassName.startsWith("ajax/")) {
            ajaxRequest = AbstractAjaxRequest.getInstance(ajaxClassName.substring(5));
        }

        if (ajaxRequest == null) {
            client.sendError(L10nServer.ACTION_NOT_FOUND);
            return;
        }

        ClientSession session = client.getSession();
        if (session == null) {
            client.sendError(L10nServer.INVALID_SESSION);
            return;
        }

        Response response = ajaxRequest.getJSON(client.getRequest());
        client.sendResponse(response);
    }
}
