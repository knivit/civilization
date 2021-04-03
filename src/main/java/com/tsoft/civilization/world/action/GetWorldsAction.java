package com.tsoft.civilization.world.action;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.action.world.GetCreateWorldFormRequest;
import com.tsoft.civilization.web.ajax.action.world.JoinWorldRequest;
import com.tsoft.civilization.web.ajax.action.world.SelectLanguageRequest;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.world.World;

import java.util.*;
import java.util.stream.Collectors;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.LANGUAGES;
import static com.tsoft.civilization.civilization.L10nCivilization.CIVILIZATIONS;
import static com.tsoft.civilization.civilization.L10nCivilization.RANDOM;
import static com.tsoft.civilization.world.L10nWorld.*;

public class GetWorldsAction {

    private static final String SELECT_LANGUAGE_REQUEST = "client.on" + SelectLanguageRequest.class.getSimpleName();
    private static final String JOIN_WORLD_REQUEST = "client.on" + JoinWorldRequest.class.getSimpleName();
    private static final String GET_CREATE_WORLD_FORM_REQUEST = "client.on" + GetCreateWorldFormRequest.class.getSimpleName();

    public static Response getWorlds() {
        StringBuilder value = Format.text("""
            $languages
            $existingWorlds
            $createNewWorld
            """,

            "$languages", getLanguages(),
            "$existingWorlds", getExistingWorlds(),
            "$createNewWorld", getCreateWorldFormRequest()
        );

        return HtmlResponse.ok(value);
    }

    private static StringBuilder getLanguages() {
        String selected = Sessions.getCurrent().getLanguage();
        selected = (selected == null) ? EN : selected;

        StringBuilder languages = new StringBuilder();
        for (Map.Entry<String, String> lang : LANGUAGES.entrySet()) {
            languages.append(Format.text("""
                <option $selected value='$code'>$name</option>
                """,

                "$selected", lang.getKey().equals(selected) ? "selected" : "",
                "$code", lang.getKey(),
                "$name", lang.getValue()
            ));
        }

        return Format.text("""
            <table id='GetWorldsRequest_action_table'>
                <tr>
                    <td width='80%' />
                    <td><select id='language' onchange="$selectLanguageRequest()">$languages</select></td>
                </tr>
            </table>
            """,

            "$selectLanguageRequest", SELECT_LANGUAGE_REQUEST,
            "$languages", languages
        );
    }

    private static StringBuilder getCivilizations() {
        StringBuilder civilizations = new StringBuilder();

        List<L10n> list = new ArrayList<>();
        list.add(RANDOM);
        list.addAll(CIVILIZATIONS.stream().collect(Collectors.toList()));

        for (L10n civilization : list) {
            civilizations.append(Format.text("""
                <option value='$code'>$name</option>
                """,

                "$code", civilization.getEnglish(),
                "$name", civilization.getLocalized()
            ));
        }
        return civilizations;
    }

    private static StringBuilder getExistingWorlds() {
        StringBuilder worlds = new StringBuilder();
        worlds.append(Format.text("""
            <tr>
                <th>$world</td>
                <th>$era</td>
                <th>$year</td>
                <th>$numOfCivilizations</td>
                <th>$slotsAvailable</td>
                <th>$action</td>
            </tr>
            """,

            "$world", WORLD_HEADER,
            "$era", ERA_HEADER,
            "$year", YEAR_HEADER,
            "$numOfCivilizations", NUM_OF_CIVILIZATIONS_HEADER,
            "$slotsAvailable", SLOTS_AVAILABLE_HEADER,
            "$action", ACTION_HEADER
        ));

        StringBuilder civilizations = getCivilizations();

        for (World world : Worlds.getWorlds()) {
            StringBuilder actions = new StringBuilder();

            int slotsAvailable = world.getMaxNumberOfCivilizations() - world.getCivilizations().size();
            if (slotsAvailable > 0) {
                // add bots for all slots (we can just view the game as a spectator)
                for (int i = 0; i < slotsAvailable; i ++) {
                    String civilizationSelector = UUID.randomUUID().toString();
                    actions.append(Format.text("""
                        <tr>
                            <td><select id='$civilizationSelector'>$civilizations</select></td>
                            <td><button onclick="$joinWorldRequest({ world:'$world', civilizationSelector:'$civilizationSelector', playerType:'$playerType' })">$addBot</button></td>
                        </tr>
                        """,

                        "$civilizations", civilizations,
                        "$civilizationSelector", civilizationSelector,
                        "$joinWorldRequest", JOIN_WORLD_REQUEST,
                        "$world", world.getId(),
                        "$playerType", PlayerType.BOT,
                        "$addBot", ADD_BOT_BUTTON
                    ));
                }

                String civilizationSelector = UUID.randomUUID().toString();
                actions.append(Format.text("""
                    <tr>
                        <td><select id='$civilizationSelector'>$civilizations</select></td>
                        <td><button onclick="$joinWorldRequest({ world:'$world', civilizationSelector:'$civilizationSelector', playerType:'$playerType' })">$join</button></td>
                    </tr>
                    """,

                    "$civilizations", civilizations,
                    "$civilizationSelector", civilizationSelector,
                    "$joinWorldRequest", JOIN_WORLD_REQUEST,
                    "$world", world.getId(),
                    "$playerType", PlayerType.HUMAN,
                    "$join", L10nWorld.JOIN_WORLD_BUTTON
                ));
            }

            String civilizationSelector = UUID.randomUUID().toString();
            actions.append(Format.text("""
                <tr>
                    <td><select id='$civilizationSelector'>$civilizations</select></td>
                    <td><button onclick="$joinWorldRequest({ world:'$world', civilizationSelector:'$civilizationSelector', playerType:'$playerType' })">$spectator</button></td>
                </tr>
                """,

                "$civilizations", civilizations,
                "$civilizationSelector", civilizationSelector,
                "$joinWorldRequest", JOIN_WORLD_REQUEST,
                "$world", world.getId(),
                "$playerType", PlayerType.SPECTATOR,
                "$spectator", L10nWorld.SPECTATOR_WORLD_BUTTON
            ));

            worlds.append(Format.text("""
                <tr>
                    <td>$worldName</td>
                    <td>$era</td>
                    <td>$year</td>
                    <td>$numberOfCivilizations</td>
                    <td>$slotsAvailable</td>
                    <td><table id='GetWorldsRequest_action_table'>$actions</table></td>
                </tr>
                """,

                "$worldName", world.getName(),
                "$era", world.getYear().getEraLocalized(),
                "$year", world.getYear().getYearLocalized(),
                "$numberOfCivilizations", world.getCivilizations().size(),
                "$slotsAvailable", slotsAvailable,
                "$actions", actions
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
                    <col width='20%' />
                  </colgroup>
                $worlds
            </table>
            """,

            "$worlds", worlds
        );
    }

    private static StringBuilder getCreateWorldFormRequest() {
        return Format.text("""
            <table id='GetWorldsRequest_action_table'>
                <tr>
                    <td><button onclick="$getCreateWorldFormRequest()">$createWorldRequest</button></td>
                    <td width='80%' />
                </tr>
            </table>
            """,

            "$getCreateWorldFormRequest", GET_CREATE_WORLD_FORM_REQUEST,
            "$createWorldRequest", L10nWorld.CREATE_NEW_WORLD_BUTTON
        );
    }
}
