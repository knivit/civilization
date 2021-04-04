package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.web.L10nClient;
import com.tsoft.civilization.util.Format;

public class GetNavigationPanel {

    public StringBuilder getContent() {
        return Format.text("""
            <table id='navigation_panel'><tr> +
                <td><button onclick="$getCivilizations">$civilizationsButton</button></td>
                <td><button onclick="$getMyCities">$citiesButton</button></td>
                <td><button onclick="$getMyUnits">$unitsButton</button></td>
                <td><button onclick="$getEvents">$showEventsButton</button></td>
            </tr></table>
            """,

            "$getCivilizations", GetCivilizations.getAjax(),
            "$civilizationsButton", L10nClient.CIVILIZATIONS_BUTTON,
            "$getMyCities", GetMyCities.getAjax(),
            "$citiesButton", L10nClient.MY_CITIES_BUTTON,
            "$getMyUnits", GetMyUnits.getAjax(),
            "$unitsButton", L10nClient.MY_UNITS_BUTTON,
            "$getEvents", GetEvents.getAjax(10000),
            "$showEventsButton", L10nClient.SHOW_EVENTS_BUTTON
        );
    }
}
