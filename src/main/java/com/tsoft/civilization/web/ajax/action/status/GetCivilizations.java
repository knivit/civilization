package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.world.World;

import java.util.List;

import static com.tsoft.civilization.civilization.L10nCivilization.BARBARIANS;

public class GetCivilizations extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();
    private final GetCivilizationInfo civilizationInfo = new GetCivilizationInfo();

    public static StringBuilder getAjax() {
        return new StringBuilder("server.sendAsyncAjax('ajax/GetCivilizations')");
    }

    @Override
    public Response getJson(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $civilizationInfo
            $civilizations
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$civilizationInfo", civilizationInfo.getContent(civilization),
            "$civilizations", getCivilizations(civilization.getWorld())
        );

        return HtmlResponse.ok(value);
    }

    private StringBuilder getCivilizations(World world) {
        List<Civilization> civilizations = world.getCivilizations().sortByName();

        Civilization myCivilization = getMyCivilization();

        StringBuilder buf = new StringBuilder();
        for (Civilization civilization : civilizations) {
            if (BARBARIANS.equals(civilization.getName())) {
                continue;
            }

            CivilizationsRelations relations = world.getCivilizationsRelations(myCivilization, civilization);

            buf.append(Format.text("""
                <tr>
                    <td><image src='$imageSrc'/><button onclick="$getCivilizationStatus">$civilizationName</button></td>
                    <td>$relations</td>
                    <td>$moveState</td>
                </tr>
                """,

                "$imageSrc", civilization.getView().getStatusImageSrc(),
                "$getCivilizationStatus", GetCivilizationStatus.getAjax(civilization),
                "$civilizationName", civilization.getView().getLocalizedCivilizationName(),
                "$relations", (relations == null) ? null : relations.getDescription(),
                "$moveState", civilization.getCivilizationMoveState().getDescription()
            ));
        }

        return Format.text("""
            <table id='actions_table'>
                <tr>
                    <th>$name</th>
                    <th>$relations</th>
                    <th>$moveState</th>
                </tr>
                $civilizations
            </table>
            """,

            "$name", L10nCivilization.CIVILIZATION_NAME,
            "$relations", L10nWorld.RELATIONS_NAME,
            "$moveState", L10nWorld.MOVE_STATE_HEADER,
            "$civilizations", buf
        );
    }
}
