package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.service.destroy.DestroyUnitService;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;

import java.util.UUID;

public class DestroyUnitAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final DestroyUnitService destroyUnitService;

    public DestroyUnitAction(DestroyUnitService destroyUnitService) {
        this.destroyUnitService = destroyUnitService;
    }

    public ActionAbstractResult destroyUnit(AbstractUnit unit) {
        return destroyUnitService.destroy(unit);
    }

    private static String getLocalizedName() {
        return L10nUnit.DESTROY_UNIT_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.DESTROY_UNIT_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(AbstractUnit unit) {
        if (destroyUnitService.canDestroy(unit).isFail()) {
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
