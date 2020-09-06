package com.tsoft.civilization.web.state;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;

import java.util.Date;
import java.util.UUID;

public class ClientSession {
    // Client-server data
    private String sessionId;
    private String ip;
    private String userAgent;
    private Date createdTime;
    private Date lastRequestTime;
    private int requestCount;
    private long receivedBytes;
    private long sendBytes;
    private String language;

    // Game data
    private String worldId;
    private String civilizationId;

    public ClientSession(String ip, String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;

        this.sessionId = UUID.randomUUID().toString();
        this.createdTime = new Date();
    }

    public String getSessionId() {
        return sessionId;
    }

    public Date getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(Date lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Civilization getCivilization() {
        World world = Worlds.getWorld(worldId);
        if (world != null) {
            return world.getCivilizationById(civilizationId);
        }
        return null;
    }

    public void setWorldAndCivilizationIds(Civilization civilization) {
        civilizationId = civilization.getId();
        worldId = civilization.getWorld().getId();
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
