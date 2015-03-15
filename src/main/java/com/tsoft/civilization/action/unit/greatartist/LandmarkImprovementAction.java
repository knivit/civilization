package com.tsoft.civilization.action.unit.greatartist;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.unit.MoveUnitActionResults;
import com.tsoft.civilization.unit.GreatArtist;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

// The Great Artist can leave their mark in history with the Landmark, where a
// tile can be used to generate 5 culture points if it is worked on. This is
// useful for new cities, where culture generation is quite important early on
// in the game.
public class LandmarkImprovementAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildLandmarkImprovement(GreatArtist unit) {
        return MoveUnitActionResults.INVALID_LOCATION;
    }

    private static ActionAbstractResult canBuildLandmarkImprovement(GreatArtist unit) {
        return MoveUnitActionResults.INVALID_LOCATION;
    }

    private static String getClientJSCode(GreatArtist unit) {
        return "not implemented";
    }

    private static String getLocalizedName() {
        return "not implemented";
    }

    private static String getLocalizedDescription() {
        return "not implemented";
    }

    public static StringBuilder getHtml(GreatArtist unit) {
        if (canBuildLandmarkImprovement(unit).isFail()) {
            return null;
        }

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td><td>$actionDescription</td>",

            "$buttonOnClick", getClientJSCode(unit),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
