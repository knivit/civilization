package com.tsoft.civilization.world.util;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.web.view.JSONBlock;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Event {
    public static final int INFORMATION = 0;
    public static final int UPDATE_WORLD = 1;
    public static final int UPDATE_CONTROL_PANEL = 2;
    public static final int UPDATE_STATUS_PANEL = 4;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.of("UTC"));

    private Object obj;

    private Instant serverEventTime;
    private L10nMap description;
    private Object[] args;
    private int flags;

    public Event(int flags, Object obj, L10nMap description, Object ... args) {
        this.flags = flags;
        this.obj = obj;
        this.description = description;
        this.args = args;

        serverEventTime = Instant.now();
    }

    public Instant getServerEventTime() {
        return serverEventTime;
    }

    public String getLocalized() {
        return description.getLocalized(args);
    }

    public boolean isUpdateWorldEvent() {
        return (flags & UPDATE_WORLD) != 0;
    }

    public boolean isUpdateControlPanelEvent() {
        return (flags & UPDATE_CONTROL_PANEL) != 0;
    }

    public boolean isUpdateStatusPanelEvent() {
        return (flags & UPDATE_STATUS_PANEL) != 0;
    }

    public JSONBlock getJSON() {
        JSONBlock block = new JSONBlock();
        block.addParam("description", getLocalized());
        block.addParam("serverTime", DATE_TIME_FORMATTER.format(serverEventTime));
        return block;
    }

    @Override
    public String toString() {
        return DATE_TIME_FORMATTER.format(serverEventTime) + " " + getLocalized();
    }
}
