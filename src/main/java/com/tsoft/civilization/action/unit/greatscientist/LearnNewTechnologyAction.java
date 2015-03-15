package com.tsoft.civilization.action.unit.greatscientist;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.unit.MoveUnitActionResults;
import com.tsoft.civilization.unit.GreatScientist;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

// The Learn New Technology ability is probably one of the most powerful
// abilities that you can get. It will pretty much instantly research a new
// technology for you, and it can be any technology that you can research at the
// current point in time, it doesn't have to be the tech that you are researching
// right now.
public class LearnNewTechnologyAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult learnNewTechnology(GreatScientist unit) {
        return MoveUnitActionResults.INVALID_LOCATION;
    }

    private static ActionAbstractResult canLearnNewTechnology(GreatScientist unit) {
        return MoveUnitActionResults.INVALID_LOCATION;
    }

    private static String getClientJSCode(GreatScientist unit) {
        return "not implemented";
    }

    private static String getLocalizedName() {
        return "not implemented";
    }

    private static String getLocalizedDescription() {
        return "not implemented";
    }

    public static StringBuilder getHtml(GreatScientist unit) {
        if (canLearnNewTechnology(unit).isFail()) {
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
