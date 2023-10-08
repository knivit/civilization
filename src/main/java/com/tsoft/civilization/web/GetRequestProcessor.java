package com.tsoft.civilization.web;

import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;

public class GetRequestProcessor {
    private GetRequestProcessor() { }

    public static Response processRequest(Request request) {
        String requestUrl = request.getRequestUrl();

        // On first request create new client's session
        String cookieHeader = null;
        if (requestUrl.isEmpty()) {
            requestUrl = "MenuPage.html";

            // Create user's session if a cookie is empty or stale
            String sessionId = request.getSessionId();
            String clientIP = request.getClientIp();
            String userAgent = request.getUserAgent();
            ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(sessionId, clientIP, userAgent);
            if (!session.getSessionId().equals(sessionId)) {
                cookieHeader = "Set-Cookie: sessionId=" + session.getSessionId() + "; HttpOnly";
            }
        }

        Response response = new Response(ResponseCode.OK, GetRequestProcessor.class, requestUrl);
        response.addHeader(cookieHeader);
        return response;
    }
}
