package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.L10n.L10nClient;
import com.tsoft.civilization.util.Format;
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

    protected StringBuilder getNavigationPanel() {
        return Format.text(
            "<table id='navigation_panel'><tr>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizations')\">$civilizationsButton</button></td>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetMyCities')\">$citiesButton</button></td>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetMyUnits')\">$unitsButton</button></td>" +

                // use year:10 000 to show last events first
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetEvents', { year:'10000' })\">$showEventsButton</button></td>" +
            "</tr></table>",

            "$civilizationsButton", L10nClient.CIVILIZATIONS_BUTTON,
            "$citiesButton", L10nClient.MY_CITIES_BUTTON,
            "$unitsButton", L10nClient.MY_UNITS_BUTTON,
            "$showEventsButton", L10nClient.SHOW_EVENTS_BUTTON
        );
    }
}
