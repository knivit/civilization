package com.tsoft.civilization.world.action;

import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.action.world.JoinWorldRequest;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.world.World;

import java.util.Map;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.LANGUAGES;

public class GetWorldsAction {
    private static final String JOIN_WORLD_REQUEST = "client.on" + JoinWorldRequest.class.getSimpleName();

    public static Response getWorlds() {
        StringBuilder value = Format.text("""
            $languages
            $existingWorlds
            $createNewWorld
            """,

            "$languages", getLanguages(),
            "$existingWorlds", getExistingWorlds(),
            "$createNewWorld", getCreateWorldRequest()
        );

        return HtmlResponse.ok(value);
    }

    private static StringBuilder getLanguages() {
        StringBuilder languages = new StringBuilder();
        for (Map.Entry<String, String> lang : LANGUAGES.entrySet()) {
            languages.append(Format.text("""
                <option $selected value='$code'>$name</option>
                """,

                "$selected", EN.equals(lang.getKey()) ? "selected" : "",
                "$code", lang.getKey(),
                "$name", lang.getValue()
            ));
        }

        return Format.text("""
            <table id='GetWorldsRequest_action_table'>
                <tr>
                    <td width='90%' />
                    <td><select id='language' onchange="client.onSelectLanguageRequest()">$languages</select></td>
                </tr>
            </table>
            """,

            "$languages", languages
        );
    }

    private static StringBuilder getExistingWorlds() {
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
                joinButton = Format.text("""
                    <button onclick="$joinWorldRequest({ world:'$world' })">$join</button>
                    """,

                    "$joinWorldRequest", JOIN_WORLD_REQUEST,
                    "$world", world.getId(),
                    "$join", L10nWorld.JOIN_WORLD_BUTTON
                );
            }

            StringBuilder spectatorButton = Format.text("""
                <button onclick="client.spectatorWorld({ world:'$world' })">$spectator</button>
                """,

                "$world", world.getId(),
                "$spectator", L10nWorld.SPECTATOR_WORLD_BUTTON
            );

            worlds.append(Format.text("""
                <tr>
                    <td>$worldName</td>
                    <td>$era</td>
                    <td>$year</td>
                    <td>$numberOfCivilizations</td>
                    <td>$slotsAvailable</td>
                    <td>$joinButton</td>
                    <td>$spectatorButton</td>
                </tr>
                """,

                "$worldName", world.getName(),
                "$era", world.getYear().getEraLocalized(),
                "$year", world.getYear().getYearLocalized(),
                "$numberOfCivilizations", world.getCivilizations().size(),
                "$slotsAvailable", slotsAvailable,

                "$joinButton", joinButton,
                "$spectatorButton", spectatorButton
            ));
        }

        return Format.text("""
            <table id='GetWorldsRequest_table'>
                <colgroup>
                    <col width='40%' />
                    <col width='10%' />
                    <col width='10%' />
                    <col width='10%' />
                    <col width='10%' />
                    <col width='10%' />
                    <col width='10%' />
                  </colgroup>
                $worlds
            </table>
            """,

            "$worlds", worlds
        );
    }

    private static StringBuilder getCreateWorldRequest() {
        return Format.text("""
            <table id='GetWorldsRequest_action_table'>
                <tr>
                    <td><button onclick="client.getCreateWorldForm()">$createWorldRequest</button></td>
                    <td width='80%' />
                </tr>
            </table>
            """,

            "$createWorldRequest", L10nWorld.CREATE_NEW_WORLD_BUTTON
        );
    }
}
