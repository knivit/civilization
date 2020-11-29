package com.tsoft.civilization.web.state;

import com.tsoft.civilization.civilization.Civilization;

import java.util.HashMap;
import java.util.Map;

public class Sessions {
    private static final Map<String, ClientSession> sessions = new HashMap<>();

    private static final ThreadLocal<ClientSession> currentSession = new ThreadLocal<>();

    public synchronized static ClientSession findOrCreateNewAndSetAsCurrent(String sessionId, String ip, String userAgent) {
        ClientSession session = sessions.get(sessionId);
        if (session == null) {
            session = new ClientSession(ip, userAgent);
            sessions.put(session.getSessionId(), session);
        }

        currentSession.set(session);
        return session;
    }

    public static void setCurrent(String sessionId) {
        if (sessionId != null) {
            currentSession.set(sessions.get(sessionId));
        }
    }

    public static ClientSession getCurrent() {
        return currentSession.get();
    }

    public static void setActiveCivilization(Civilization civilization) {
        getCurrent().setActiveCivilization(civilization);
    }
}
