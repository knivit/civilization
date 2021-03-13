package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.web.L10nClient;
import com.tsoft.civilization.util.Format;

public class GetNavigationPanel {

    public StringBuilder getContent() {
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
