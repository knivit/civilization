package com.tsoft.civilization.action.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

public class DestroyUnitAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult destroyUnit(AbstractUnit unit) {
        ActionAbstractResult result = canDestroyUnit(unit);
        if (result.isFail()) {
            return result;
        }

        unit.destroyBy(null, false);

        return DestroyUnitResults.UNIT_DESTROYED;
    }

    private static ActionAbstractResult canDestroyUnit(AbstractUnit unit) {
        if (unit == null || unit.isDestroyed()) {
            return DestroyUnitResults.UNIT_NOT_FOUND;
        }

        if (unit.getPassScore() <= 0) {
            return DestroyUnitResults.NO_PASS_SCORE;
        }

        return DestroyUnitResults.CAN_DESTROY_UNIT;
    }

    private static String getClientJSCode(AbstractUnit unit) {
        return String.format("client.destroyUnitAction({ unit:'%1$s' })", unit.getId());
    }

    private static String getLocalizedName() {
        return L10nUnit.DESTROY_UNIT_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.DESTROY_UNIT_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(AbstractUnit unit) {
        if (canDestroyUnit(unit).isFail()) {
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
