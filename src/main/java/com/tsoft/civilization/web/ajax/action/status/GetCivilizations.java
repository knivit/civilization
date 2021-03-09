package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.civilization.CivilizationList;

public class GetCivilizations extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $civilizations
            """,

            "$navigationPanel", getNavigationPanel(),
            "$civilizations", getCivilizations(myCivilization.getWorld())
        );

        return new HtmlResponse(ResponseCode.OK, value.toString());
    }

    private StringBuilder getCivilizations(World world) {
        CivilizationList civilizations = world.getCivilizations();

        civilizations.sortByName();
        Civilization myCivilization = getMyCivilization();

        StringBuilder buf = new StringBuilder();
        for (Civilization civilization : civilizations) {
            CivilizationsRelations relations = world.getCivilizationsRelations(myCivilization, civilization);

            buf.append(Format.text(
                "<tr>" +
                    "<td><image src='$imageSrc'/></td>" +
                    "<td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilizationId' })\">$civilizationName</button></td>" +
                    "<td>$relations</td>" +
                "</tr>",

                "$imageSrc", civilization.getView().getStatusImageSrc(),
                "$civilizationId", civilization.getId(),
                "$civilizationName", civilization.getView().getLocalizedCivilizationName(),
                "$relations", (relations == null ? "" : relations.getDescription().toString())
            ));
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr><th colspan='2'>$name</th><th>$relations</th></tr>" +
                "$civilizations" +
            "</table>",

            "$name", L10nCivilization.CIVILIZATION_NAME,
            "$relations", L10nWorld.RELATIONS_NAME,
            "$civilizations", buf
        );
    }
}
