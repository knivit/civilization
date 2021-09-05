package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.world.*;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;

import static com.tsoft.civilization.world.Climate.NORMAL;
import static com.tsoft.civilization.world.MapConfiguration.EARTH;
import static com.tsoft.civilization.world.MapSize.DUEL;

public class GetCreateWorldFormRequest extends AbstractAjaxRequest {

    private static final String GET_WORLDS_REQUEST = "client.on" + GetWorldsRequest.class.getSimpleName();
    private static final String CREATE_WORLD_REQUEST = "client.on" + CreateWorldRequest.class.getSimpleName();

    @Override
    public Response getJson(Request request) {
        StringBuilder backButton = Format.text(
            "<button onclick='$getWorldsRequest();'>$backButton</button>",

            "$getWorldsRequest", GET_WORLDS_REQUEST,
            "$backButton", L10nWorld.BACK_BUTTON
        );

        StringBuilder createWorldButton = Format.text(
            "<button onclick='$createWorldRequest();'>$createWorldButton</button>",

            "$createWorldRequest", CREATE_WORLD_REQUEST,
            "$createWorldButton", L10nWorld.CREATE_NEW_WORLD_BUTTON
        );

        StringBuilder value = Format.text("""
            <table id='actions_table'>
                <tr>
                    <td>$worldName</td>
                    <td><input id='worldName' type='text' placeholder='My World' /></td>
                </tr>
                <tr>
                    <td>$mapConfiguration</td>
                    <td><select id='mapConfiguration'>$mapConfigurationOptions</select>
                </tr>
                <tr>
                    <td>$mapSize</td>
                    <td><select id='mapSize'>$mapSizeOptions</select>
                </tr>
                <tr>
                    <td>$climate</td>
                    <td><select id='climate'>$climateOptions</select>
                </tr>
                <tr>
                    <td>$difficultyLevel</td>
                    <td><select id='difficultyLevel'>$difficultyLevelOptions</select>
                    </td>
                </tr>
                <tr>
                    <td>$backButton</td>
                    <td>$createWorldButton</td>
                </tr>
            </table>
            """,

            "$worldName", L10nWorld.INPUT_WORLD_NAME,
            "$mapConfiguration", L10nWorld.INPUT_MAP_CONFIGURATION,
            "$mapConfigurationOptions", getMapConfigurationOptions(),
            "$mapSize", L10nWorld.INPUT_MAP_SIZE,
            "$mapSizeOptions", getMapSizeOptions(),
            "$climate", L10nWorld.INPUT_CLIMATE,
            "$climateOptions", getClimateOptions(),
            "$difficultyLevel", L10nWorld.DIFFICULTY_LEVEL_NAME,
            "$difficultyLevelOptions", getDifficultyLevelOptions(),

            "$backButton", backButton,
            "$createWorldButton", createWorldButton
        );

        return HtmlResponse.ok(value);
    }

    private StringBuilder getMapConfigurationOptions() {
        StringBuilder buf = new StringBuilder();
        for (MapConfiguration conf : MapConfiguration.values()) {
            buf.append(Format.text("<option $selected value='$value' description='$desc'>$text</option>",
                "$selected", EARTH.equals(conf) ? "selected" : "",
                "$value", conf.name(),
                "$text", conf.name(),
                "$desc", conf.getDescription()));
        }
        return buf;
    }

    private StringBuilder getMapSizeOptions() {
        StringBuilder buf = new StringBuilder();
        for (MapSize size : MapSize.values()) {
            buf.append(Format.text("<option $selected value='$value'>$text</option>",
                "$selected", DUEL.equals(size) ? "selected" : "",
                "$value", size.name(),
                "$text", size.getL10n()));

        }
        return buf;
    }

    private StringBuilder getClimateOptions() {
        StringBuilder buf = new StringBuilder();
        for (Climate climate : Climate.values()) {
            buf.append(Format.text("<option $selected value='$value'>$text</option>",
                "$selected", NORMAL.equals(climate) ? "selected" : "",
                "$value", climate.name(),
                "$text", climate.getL10n()));

        }
        return buf;
    }

    private StringBuilder getDifficultyLevelOptions() {
        StringBuilder buf = new StringBuilder();
        for (DifficultyLevel level : DifficultyLevel.values()) {
            buf.append(Format.text("<option value='$value'>$text</option>",
                "$value", level.name(),
                "$text", level.getL10n()));

        }
        return buf;
    }
}
