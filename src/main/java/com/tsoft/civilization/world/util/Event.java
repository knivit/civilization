package com.tsoft.civilization.world.util;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.web.view.JSONBlock;

import java.util.Date;

public class Event {
    public static final int INFORMATION = 0;
    public static final int UPDATE_WORLD = 1;
    public static final int UPDATE_CONTROL_PANEL = 2;
    public static final int UPDATE_STATUS_PANEL = 4;

    private Object obj;

    private Date serverEventTime;
    private L10nMap description;
    private int flags;

    public Event(Object obj, L10nMap description, int flags) {
        this.obj = obj;
        this.description = description;
        this.flags = flags;

        serverEventTime = new Date();
    }

    public Date getServerEventTime() {
        return serverEventTime;
    }

    public L10nMap getDescription() {
        return description;
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
        block.addParam("description", description);
        block.addParam("serverTime", String.format("%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %1$Tp", serverEventTime));
        return block;
    }
}
