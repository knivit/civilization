package com.tsoft.civilization.unit.catalog.greatscientist.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

import static com.tsoft.civilization.unit.action.move.MoveUnitService.INVALID_TARGET_LOCATION;

// The Academy is an improvement that will boost the amount of science that is
// generated. When an academy tile is worked, it will generate 5 science points
// for your scientific endeavours. This is useful early on, where tech costs a
// lot less, it is less relevant in the modern age where tech is quite expensive
// to research.
public class AcademyImprovementAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildAcademyImprovement(AbstractUnit unit) {
        return INVALID_TARGET_LOCATION;
    }

    private static ActionAbstractResult canBuildAcademyImprovement(AbstractUnit unit) {
        return INVALID_TARGET_LOCATION;
    }

    private static String getClientJSCode(AbstractUnit unit) {
        return "not implemented";
    }

    private static String getLocalizedName() {
        return "not implemented";
    }

    private static String getLocalizedDescription() {
        return "not implemented";
    }

    public static StringBuilder getHtml(AbstractUnit unit) {
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
