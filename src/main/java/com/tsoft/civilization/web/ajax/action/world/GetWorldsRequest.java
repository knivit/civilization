package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.World;

public class GetWorldsRequest extends AbstractAjaxRequest {
    private static final String JOIN_WORLD_REQUEST = "client.on" + JoinWorldRequest.class.getSimpleName();

    @Override
    public Response getJson(Request request) {
        StringBuilder value = Format.text(
            """
            $existingWorlds
            $createNewWorld
            """,

            "$existingWorlds", getExistingWorlds(),
            "$createNewWorld", getCreateWorldRequest()
        );

        return new HtmlResponse(ResponseCode.OK, value.toString());
    }

    private StringBuilder getExistingWorlds() {
        StringBuilder worlds = new StringBuilder();
        worlds.append("""
            <tr>
                <th>World</td>
                <th>Era</td>
                <th>Year</td>
                <th>Num of Civilizations</td>
                <th>Slots Available</td>
                <th>Join</td>
                <th>View</td>
            </tr>
            """);

        for (World world : Worlds.getWorlds()) {
            int slotsAvailable = world.getMaxNumberOfCivilizations() - world.getCivilizations().size();
            StringBuilder joinButton = null;
            if (slotsAvailable > 0) {
                joinButton = Format.text(
                    "<button onclick=\"$joinWorldRequest({ world:'$world' })\">$join</button>",

                    "$joinWorldRequest", JOIN_WORLD_REQUEST,
                    "$world", world.getId(),
                    "$join", L10nWorld.JOIN_WORLD_BUTTON
                );
            }

            StringBuilder spectatorButton = Format.text(
                "<button onclick=\"client.spectatorWorld({ world:'$world' })\">$spectator</button>",

                "$world", world.getId(),
                "$spectator", L10nWorld.SPECTATOR_WORLD_BUTTON
            );

            worlds.append(Format.text(
                "<tr>" +
                    "<td>$worldName</td>" +
                    "<td>$era</td>" +
                    "<td>$year</td>" +
                    "<td>$numberOfCivilizations</td>" +
                    "<td>$slotsAvailable</td>" +
                    "<td>$joinButton</td>" +
                    "<td>$spectatorButton</td>" +
                "</tr>",

                "$worldName", world.getName(),
                "$era", world.getYear().getEraLocalized(),
                "$year", world.getYear().getYearLocalized(),
                "$numberOfCivilizations", world.getCivilizations().size(),
                "$slotsAvailable", slotsAvailable,

                "$joinButton", joinButton,
                "$spectatorButton", spectatorButton
            ));
        }

        return Format.text(
            "<table id='actions_table'>" +
                "$worlds" +
            "</table>",

            "$worlds", worlds
        );
    }

    private StringBuilder getCreateWorldRequest() {
        return Format.text(
            "<table id='actions_table'>" +
                "<tr><button onclick=\"client.getCreateWorldForm()\">$createWorldRequest</button></tr>" +
            "</table>",

            "$createWorldRequest", L10nWorld.CREATE_NEW_WORLD_BUTTON
        );
    }
}
