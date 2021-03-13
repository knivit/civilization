package com.tsoft.civilization.unit.civil.greatgeneral.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.civil.greatgeneral.GreatGeneral;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

import static com.tsoft.civilization.unit.action.MoveUnitAction.INVALID_LOCATION;

// Combat Bonus is basically where all friendly units within 2 tiles of the
// General gets a boost to their combat abilities, regardless of their combat
// type. That applies to both offensive and defensive combat, it doesn't matter,
// they just fight better.
public class CombatBonusAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult combatBonus(GreatGeneral unit) {
        return INVALID_LOCATION;
    }

    private static ActionAbstractResult canCombatBonus(GreatGeneral unit) {
        return INVALID_LOCATION;
    }

    private static String getClientJSCode(GreatGeneral unit) {
        return "not implemented";
    }

    private static String getLocalizedName() {
        return "not implemented";
    }

    private static String getLocalizedDescription() {
        return "not implemented";
    }

    public static StringBuilder getHtml(GreatGeneral unit) {
        if (canCombatBonus(unit).isFail()) {
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
