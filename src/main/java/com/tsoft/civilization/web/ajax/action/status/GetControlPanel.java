package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.civilization.action.NextTurnAction;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

public class GetControlPanel extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $civilizationInfo
            $controls
            """,

            "$civilizationInfo", getCivilizationInfo(civilization),
            "$controls", getControls(civilization));
        return HtmlResponse.ok(value);
    }

    private StringBuilder getCivilizationInfo(Civilization civilization) {
        return Format.text("""
            <table id='control_table'>
                <tr><td>$name, $year</td></tr>
            </table>
            """,

            "$name", civilization.getView().getLocalizedCivilizationName(),
            "$year", civilization.getWorld().getYear().getYearLocalized()
        );
    }

    private StringBuilder getControls(Civilization civilization) {
        UnitList units = civilization.units().getUnitsWithActionsAvailable();
        CityList cities = civilization.cities().getCitiesWithActionsAvailable();

        StringBuilder controls = new StringBuilder();
        if (!units.isEmpty() || !cities.isEmpty()) {
            controls.append(Format.text("""
                <td><button onclick="server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })">$actions</button></td>
                """,

                "$civilization", civilization.getId(),
                "$actions", L10nCivilization.AVAILABLE_ACTIONS
            ));
        }

        return Format.text("""
            <table id='control_table'>
                <tr>$controls $nextTurnAction</tr>
            </table>
            """,

            "$nextTurnAction", NextTurnAction.getHtml(civilization),
            "$controls", controls
        );
    }
}
