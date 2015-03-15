package com.tsoft.civilization.web;

import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;

public class GetRequestProcessor {
    private GetRequestProcessor() { }

    public static void processRequest(ServerClient client) {
        String fileName = client.getRequest().getRequestUrl();

        // On first request create new client's session
        String cookieHeader = null;
        if (fileName.length() == 0) {
            fileName = "MenuPage.html";

            // Create an user's session if cookie was empty or it is stale
            String sessionId = client.getRequest().getSessionId();
            String clientIP = client.getRequest().getClientIP();
            String userAgent = client.getRequest().getUserAgent();
            ClientSession session = Sessions.findOrCreateNewAndSetAsCurrent(sessionId, clientIP, userAgent);
            if (!session.getSessionId().equals(sessionId)) {
                cookieHeader = "Set-Cookie: sessionId=" + session.getSessionId() + "; HttpOnly";
            }
        }

        Response response = new Response(ResponseCode.OK, GetRequestProcessor.class, fileName);
        response.addHeader(cookieHeader);
        client.sendResponse(response);
    }
}
