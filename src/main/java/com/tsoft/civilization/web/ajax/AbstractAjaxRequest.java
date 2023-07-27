package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;

public abstract class AbstractAjaxRequest {

    public abstract Response getJson(Request request);

    /** Returns null if requested AJAX doesn't found */
    public static AbstractAjaxRequest getInstance(String requestName) {
        return RequestsMap.get(requestName);
    }

    protected World getWorld() {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization != null) {
            return myCivilization.getWorld();
        }
        return null;
    }

    protected Civilization getMyCivilization() {
        ClientSession mySession = Sessions.getCurrent();
        return mySession.getCivilization();
    }
}
