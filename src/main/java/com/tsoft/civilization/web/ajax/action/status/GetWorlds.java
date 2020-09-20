package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.World;

public class GetWorlds extends AbstractAjaxRequest {
    @Override
    public Response getJson(Request request) {
        StringBuilder value = Format.text(
            "$existingWorlds\n" +
            "$createNewWorld\n",

            "$existingWorlds", getExistingWorlds(),
            "$createNewWorld", getCreateWorld()
        );

        return new Response(ResponseCode.OK, value.toString(), ContentType.TEXT_HTML);
    }

    private StringBuilder getExistingWorlds() {
        StringBuilder worlds = new StringBuilder();
        for (World world : Worlds.getWorlds()) {
            int slotsAvailable = world.getMaxNumberOfCivilizations() - world.getCivilizations().size();
            StringBuilder joinButton = null;
            if (slotsAvailable > 0) {
                joinButton = Format.text(
                    "<button onclick=\"client.joinWorld({ world:'$world' })\">$join</button>",

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
                "</tr>" +
                "<tr>" +
                    "<td colspan='5'>$joinButton $spectatorButton</td>" +
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

    private StringBuilder getCreateWorld() {
        return Format.text(
            "<table id='actions_table'>" +
                "<tr><button onclick=\"client.getCreateWorldForm()\">$createWorld</button></tr>" +
            "</table>",

            "$createWorld", L10nWorld.CREATE_NEW_WORLD_BUTTON
        );
    }
}
