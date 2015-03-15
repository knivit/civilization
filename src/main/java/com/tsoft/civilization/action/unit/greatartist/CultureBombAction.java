package com.tsoft.civilization.action.unit.greatartist;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.unit.MoveUnitActionResults;
import com.tsoft.civilization.unit.GreatArtist;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

// The Culture Bomb ability is quite useful, where the tile that the artist is
// standing on, as well as the 6 surrounding hex tiles, will be converted to
// your empire. This includes foreign land, where they will be turned to your
// empire. Although they cannot be used to convert cities, it is a useful tactic
// to culture bomb an enemy city, to convert their best food or production tiles
// to your empire, and even though you can't use it, you can deny it's use to the enemy.
public class CultureBombAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult cultureBomb(GreatArtist unit) {
        return MoveUnitActionResults.INVALID_LOCATION;
    }

    private static ActionAbstractResult canCultureBomb(GreatArtist unit) {
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
        if (canCultureBomb(unit).isFail()) {
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
