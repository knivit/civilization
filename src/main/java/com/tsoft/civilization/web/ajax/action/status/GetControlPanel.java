package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.action.civilization.NextMoveAction;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.util.ContentType;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.world.Civilization;

public class GetControlPanel extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text(
            "$civilizationInfo\n" +
            "$controls\n",

            "$civilizationInfo", getCivilizationInfo(civilization),
            "$controls", getControls(civilization));
        return new Response(ResponseCode.OK, value.toString(), ContentType.TEXT_HTML);
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
        UnitCollection units = civilization.getUnitsWithActionsAvailable();
        CityCollection cities = civilization.getCitiesWithActionsAvailable();

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
