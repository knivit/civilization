package com.tsoft.civilization.unit.service.destroy;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.catalog.settlers.Settlers;

public class DestroyUnitService {

    public static final ActionSuccessResult UNIT_DESTROYED = new ActionSuccessResult(L10nUnit.UNIT_DESTROYED);
    public static final ActionSuccessResult CAN_DESTROY_UNIT = new ActionSuccessResult(L10nUnit.CAN_DESTROY_UNIT);

    public static final ActionFailureResult UNIT_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult NO_PASS_SCORE = new ActionFailureResult(L10nUnit.NO_PASS_SCORE);
    public static final ActionFailureResult LAST_SETTLERS_CANT_BE_DESTROYED = new ActionFailureResult(L10nUnit.LAST_SETTLERS_CANT_BE_DESTROYED);

    public ActionAbstractResult destroy(AbstractUnit unit) {
        ActionAbstractResult result = canDestroy(unit);
        if (result.isFail()) {
            return result;
        }

        Civilization civilization = unit.getCivilization();
        civilization.getUnitService().destroyUnit(unit);

        return UNIT_DESTROYED;
    }

    public ActionAbstractResult canDestroy(AbstractUnit unit) {
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
}
