package com.tsoft.civilization.action.unit.greatscientist;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.unit.MoveUnitActionResults;
import com.tsoft.civilization.unit.civil.GreatScientist;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

// The Academy is an improvement that will boost the amount of science that is
// generated. When an academy tile is worked, it will generate 5 science points
// for your scientific endeavours. This is useful early on, where tech costs a
// lot less, it is less relevant in the modern age where tech is quite expensive
// to research.
public class AcademyImprovementAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildAcademyImprovement(GreatScientist unit) {
        return MoveUnitActionResults.INVALID_LOCATION;
    }

    private static ActionAbstractResult canBuildAcademyImprovement(GreatScientist unit) {
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
        if (canBuildAcademyImprovement(unit).isFail()) {
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
