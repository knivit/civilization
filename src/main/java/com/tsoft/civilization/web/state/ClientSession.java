package com.tsoft.civilization.web.state;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;

import java.util.Date;
import java.util.UUID;

import static com.tsoft.civilization.L10n.L10n.DEFAULT_LANGUAGE;

public class ClientSession {
    // Client-server data
    private final String sessionId;
    private final String ip;
    private final String userAgent;
    private final Date createdTime;
    private String language;

    // Game data
    private String worldId;
    private String civilizationId;

    public ClientSession(String ip, String userAgent) {
        this.sessionId = UUID.randomUUID().toString();
        this.createdTime = new Date();

        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getLanguage() {
        return language;
    }

    public ClientSession setLanguage(String language) {
        this.language = (language == null) ? DEFAULT_LANGUAGE : language;
        return this;
    }

    public Civilization getCivilization() {
        if (worldId != null) {
            World world = Worlds.getWorld(worldId);
            if (world != null && civilizationId != null) {
                return world.getCivilizationById(civilizationId);
            }
        }

        return null;
    }

    public ClientSession setActiveCivilization(Civilization civilization) {
        if (civilization == null) {
            civilizationId = null;
            worldId = null;
        } else {
            civilizationId = civilization.getId();
            worldId = civilization.getWorld().getId();
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientSession that = (ClientSession) o;

        if (!sessionId.equals(that.sessionId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }
}
