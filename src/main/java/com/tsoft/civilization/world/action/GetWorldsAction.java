package com.tsoft.civilization.world.action;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.*;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.action.world.GetCreateWorldFormRequest;
import com.tsoft.civilization.web.ajax.action.world.JoinWorldRequest;
import com.tsoft.civilization.web.ajax.action.world.SelectLanguageRequest;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.world.World;

import java.util.*;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.LANGUAGES;
import static com.tsoft.civilization.civilization.L10nCivilization.*;
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
        ClientSession session = Sessions.getCurrent();
        String selected = (session == null || session.getLanguage() == null) ? EN : session.getLanguage();

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
                    <td width='75%' />
                    <td><select id='language' onchange="$selectLanguageRequest()">$languages</select></td>
                </tr>
            </table>
            """,

            "$selectLanguageRequest", SELECT_LANGUAGE_REQUEST,
            "$languages", languages
        );
    }

    private static StringBuilder getExistingWorlds() {
        StringBuilder worlds = new StringBuilder();
        worlds.append(Format.text("""
            <tr>
                <th>$world</td>
                <th>$civilizations</td>
                <th>$slotsAvailable</td>
                <th>$action</td>
            </tr>
            """,

            "$world", WORLD_HEADER,
            "$civilizations", CIVILIZATIONS_HEADER,
            "$slotsAvailable", SLOTS_AVAILABLE_HEADER,
            "$action", ACTION_HEADER
        ));

        StringBuilder civilizations = getCivilizationsSelect();
        for (World world : Worlds.getWorlds()) {
            StringBuilder actions = new StringBuilder();

            int civilizationsCount = (int)world.getCivilizations().stream()
                .filter(e -> !BARBARIANS.equals(e.getName()))
                .count();

            int slotsAvailable = world.getMaxNumberOfCivilizations() - civilizationsCount;
            if (slotsAvailable > 0) {
                // add a human
                actions.append(addPlayer(civilizations, world, PlayerType.HUMAN, L10nWorld.JOIN_WORLD_BUTTON));

                // add bots for all slots (we can just view the game as a spectator)
                for (int i = 0; i < slotsAvailable; i ++) {
                    actions.append(addPlayer(civilizations, world, PlayerType.BOT, L10nWorld.ADD_BOT_BUTTON));
                }
            }

            actions.append(addPlayer(civilizations, world, PlayerType.SPECTATOR, L10nWorld.SPECTATOR_WORLD_BUTTON));

            worlds.append(Format.text("""
                <tr>
                    <td>
                        <table id='GetWorldsRequest_world_info_table'>
                            <tr><td>$nameLabel</td><td>$worldName</td></tr>
                            <tr><td>$eraLabel</td><td>$era</td></tr>
                            <tr><td>$yearLabel</td><td>$year</td></tr>
                            <tr><td>$mapSizeLabel</td><td>$mapSize</td></tr>
                            <tr><td>$mapConfLabel</td><td>$mapConf</td></tr>
                            <tr><td>$climateLabel</td><td>$climate</td></tr>
                            <tr><td>$difficultyLevelLabel</td><td>$difficultyLevel</td></tr>
                        </table>
                    </td>
                    <td>$civilizations</td>
                    <td>$slotsAvailable</td>
                    <td><table id='GetWorldsRequest_action_table'>$actions</table></td>
                </tr>
                """,

                "$nameLabel", WORLD_NAME_LABEL,
                "$worldName", world.getName(),
                "$eraLabel", ERA_LABEL,
                "$era", world.getYear().getEraLocalized(),
                "$yearLabel", YEAR_LABEL,
                "$year", world.getYear().getYearLocalized(),
                "$mapSizeLabel", MAP_SIZE_LABEL,
                "$mapSize", world.getTilesMap().getMapSize().getL10n().getLocalized(),
                "$mapConfLabel", MAP_CONF_LABEL,
                "$mapConf", world.getTilesMap().getMapConfiguration().name(),
                "$climateLabel", CLIMATE_LABEL,
                "$climate", world.getClimate().getL10n().getLocalized(),
                "$difficultyLevelLabel", DIFFICULTY_LEVEL_LABEL,
                "$difficultyLevel", world.getClimate().getL10n().getLocalized(),
                "$civilizations", getCivilizationsTable(world),
                "$slotsAvailable", slotsAvailable,
                "$actions", actions
            ));
        }

        return Format.text("""
            <table id='GetWorldsRequest_table'>
                <colgroup>
                    <col width='35%' />
                    <col width='30%' />
                    <col width='10%' />
                    <col width='25%' />
                  </colgroup>
                $worlds
            </table>
            """,

            "$worlds", worlds
        );
    }

    private static StringBuilder getCivilizationsTable(World world) {
        List<Civilization> civilizations = world.getCivilizations().sortByName();

        StringBuilder buf = new StringBuilder();
        for (Civilization civilization : civilizations) {
            if (BARBARIANS.equals(civilization.getName())) {
                continue;
            }

            buf.append(Format.text("""
                <tr>
                    <td><image src='$imageSrc'/></td>
                    <td>$civilizationName</td>
                </tr>
                """,

                "$imageSrc", civilization.getView().getStatusImageSrc(),
                "$civilizationName", civilization.getView().getLocalizedCivilizationName()
            ));
        }

        return Format.text("""
            <table id='actions_table'>
                $civilizations
            </table>
            """,

            "$civilizations", buf
        );
    }

    private static StringBuilder getCivilizationsSelect() {
        StringBuilder civilizations = new StringBuilder();

        List<L10n> list = new ArrayList<>();
        list.add(RANDOM);
        list.addAll(CIVILIZATIONS.stream().toList());

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
    private static StringBuilder addPlayer(StringBuilder civilizations, World world, PlayerType playerType, L10n joinButtonMessage) {
        String civilizationSelector = UUID.randomUUID().toString();

        return Format.text("""
                    <tr>
                        <td><select id='$civilizationSelector'>$civilizations</select></td>
                        <td><button onclick="$joinWorldRequest({ world:'$world', civilizationSelector:'$civilizationSelector', playerType:'$playerType' })">$join</button></td>
                    </tr>
                    """,

            "$civilizations", civilizations,
            "$civilizationSelector", civilizationSelector,
            "$joinWorldRequest", JOIN_WORLD_REQUEST,
            "$world", world.getId(),
            "$playerType", playerType,
            "$join", joinButtonMessage
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
