package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;

public class GetCreateWorldForm extends AbstractAjaxRequest {
    @Override
    public Response getJson(Request request) {
        StringBuilder backButton = Format.text(
            "<button onclick='client.getWorlds();'>$backButton</button>",

            "$backButton", L10nWorld.BACK_BUTTON
        );

        StringBuilder createWorldButton = Format.text(
            "<button onclick='client.createWorld();'>createWorldButton</button>",

            "createWorldButton", L10nWorld.CREATE_NEW_WORLD_BUTTON
        );

        StringBuilder value = Format.text(
            "<table id='actions_table'>" +
                "<tr><td>$worldName</td><td><input id='worldName' type='text' placeholder='My World' /></td></tr>" +
                "<tr><td>$worldType</td><td>" +
                    "<select id='worldType'>" +
                        "<option value='0'>Earth</option>" +
                        "<option selected value='1'>One big continent</option>" +
                    "</select></td></tr>" +
                "<tr><td>$mapWidth</td><td><input id='mapWidth' type='text' placeholder='20' /></td></tr>" +
                "<tr><td>$mapHeight</td><td><input id='mapHeight' type='text' placeholder='20' /></td></tr>" +
                "<tr><td>$climate</td><td>" +
                    "<select id='climate'>" +
                        "<option value='0'>Cold</option>" +
                        "<option selected value='1'>Normal</option>" +
                        "<option value='2'>Hot</option>" +
                    "</select></td></tr>" +
                "<tr><td>$maxNumberOfCivilizations</td><td><input id='maxNumberOfCivilizations' type='text' placeholder='4' /></td></tr>" +
                "<tr><td>$language</td><td>" +
                    "<select id='language'>" +
                        "<option selected value='en'>English</option>" +
                        "<option value='ru'>Russian</option>" +
                    "</select></td></tr>" +
                "<tr><td>$backButton</td><td>$createWorldButton</td></tr>" +
            "</table>",

            "$worldName", L10nWorld.INPUT_WORLD_NAME,
            "$worldType", L10nWorld.INPUT_WORLD_TYPE,
            "$mapWidth", L10nWorld.INPUT_MAP_WIDTH,
            "$mapHeight", L10nWorld.INPUT_MAP_HEIGHT,
            "$climate", L10nWorld.INPUT_CLIMATE,
            "$maxNumberOfCivilizations", L10nWorld.INPUT_MAX_NUMBER_OF_CIVILIZATIONS,
            "$language", L10nWorld.INPUT_LANGUAGE,

            "$backButton", backButton,
            "$createWorldButton", createWorldButton
        );

        return new HtmlResponse(ResponseCode.OK, value.toString());
    }
}
