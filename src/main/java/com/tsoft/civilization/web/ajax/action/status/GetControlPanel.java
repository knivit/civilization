package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.civilization.action.NextMoveAction;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

public class GetControlPanel extends AbstractAjaxRequest {
    @Override
    public Response getJson(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text(
            "$civilizationInfo\n" +
            "$controls\n",

            "$civilizationInfo", getCivilizationInfo(civilization),
            "$controls", getControls(civilization));
        return new HtmlResponse(ResponseCode.OK, value.toString());
    }

    private StringBuilder getCivilizationInfo(Civilization civilization) {
        return Format.text(
            "<table id='control_table'><tr>" +
                "<td>$name, $year</td>" +
            "</tr></table>",

            "$name", civilization.getView().getLocalizedCivilizationName(),
            "$year", civilization.getWorld().getYear().getYearLocalized()
        );
    }

    private StringBuilder getControls(Civilization civilization) {
        UnitList<?> units = civilization.units().getUnitsWithActionsAvailable();
        CityList cities = civilization.cities().getCitiesWithActionsAvailable();

        StringBuilder controls = new StringBuilder();
        if (!units.isEmpty() || !cities.isEmpty()) {
            controls.append(Format.text(
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })\">$actions</button></td>",

                "$civilization", civilization.getId(),
                "$actions", L10nCivilization.AVAILABLE_ACTIONS
            ));
        }

        return Format.text(
            "<table id='control_table'>" +
                "<tr>$nextMoveAction $controls</tr>" +
            "</table>",

            "$nextMoveAction", NextMoveAction.getHtml(civilization),
            "$controls", controls
        );
    }
}
