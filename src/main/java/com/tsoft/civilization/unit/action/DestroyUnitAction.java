package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;

import java.util.UUID;

public class DestroyUnitAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult UNIT_DESTROYED = new ActionSuccessResult(L10nUnit.UNIT_DESTROYED);
    public static final ActionSuccessResult CAN_DESTROY_UNIT = new ActionSuccessResult(L10nUnit.CAN_DESTROY_UNIT);

    public static final ActionFailureResult UNIT_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult NO_PASS_SCORE = new ActionFailureResult(L10nUnit.NO_PASS_SCORE);
    public static final ActionFailureResult LAST_SETTLERS_CANT_BE_DESTROYED = new ActionFailureResult(L10nUnit.LAST_SETTLERS_CANT_BE_DESTROYED);

    public static ActionAbstractResult destroyUnit(AbstractUnit unit) {
        ActionAbstractResult result = canDestroyUnit(unit);
        if (result.isFail()) {
            return result;
        }

        Civilization civilization = unit.getCivilization();
        civilization.getUnitService().destroyUnit(unit);

        return UNIT_DESTROYED;
    }

    private static ActionAbstractResult canDestroyUnit(AbstractUnit unit) {
        if (unit == null || unit.isDestroyed()) {
            return UNIT_NOT_FOUND;
        }

        if (unit.getPassScore() <= 0) {
            return NO_PASS_SCORE;
        }

        if (unit instanceof Settlers && unit.getCivilization().getUnitService().size() == 1) {
            return LAST_SETTLERS_CANT_BE_DESTROYED;
        }

        return CAN_DESTROY_UNIT;
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

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", ClientAjaxRequest.destroyUnitAction(unit),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
