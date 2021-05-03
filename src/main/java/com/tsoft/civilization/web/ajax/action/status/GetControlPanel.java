package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.web.L10nClient;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.civilization.action.NextTurnAction;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

public class GetControlPanel extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $informationBoard
            $controls
            """,

            "$informationBoard", getInformationBoard(),
            "$controls", getControls(civilization));
        return HtmlResponse.ok(value);
    }

    private StringBuilder getInformationBoard() {
        return new StringBuilder("""
            <table id='control_table'>
                <tr><td><div id='informationBoard'></div></td></tr>
            </table>
            """);
    }

    private StringBuilder getControls(Civilization civilization) {
        UnitList units = civilization.getUnitService().getUnitsWithActionsAvailable();
        CityList cities = civilization.getCityService().getCitiesWithAvailableActions();

        StringBuilder controls = new StringBuilder();
        if (!units.isEmpty() || !cities.isEmpty()) {
            controls.append(Format.text("""
                <td><button onclick="$getCivilizationStatus">$actions</button></td>
                """,

                "$getCivilizationStatus", GetCivilizationStatus.getAjax(civilization),
                "$actions", L10nCivilization.AVAILABLE_ACTIONS
            ));
        }

        return Format.text("""
            <table id='control_table'>
                <tr>
                    <td><button onclick="$getCivilizations">$civilizationsButton</button></td>
                    $controls
                    $nextTurnAction
                </tr>
            </table>
            """,

            "$getCivilizations", GetCivilizations.getAjax(),
            "$civilizationsButton", L10nClient.CIVILIZATIONS_BUTTON,
            "$nextTurnAction", NextTurnAction.getHtml(civilization),
            "$controls", controls
        );
    }
}
