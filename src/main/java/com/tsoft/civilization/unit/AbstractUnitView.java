package com.tsoft.civilization.unit;

import com.tsoft.civilization.unit.action.AttackAction;
import com.tsoft.civilization.unit.action.CaptureUnitAction;
import com.tsoft.civilization.unit.action.DestroyUnitAction;
import com.tsoft.civilization.unit.action.MoveUnitAction;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.view.JSONBlock;

public abstract class AbstractUnitView<U extends AbstractUnit<?>> {
    public abstract String getLocalizedName();
    public abstract String getLocalizedDescription();
    public abstract String getJSONName();
    public abstract String getStatusImageSrc();

    public JSONBlock getJSON(U unit) {
        JSONBlock unitBlock = new JSONBlock();
        unitBlock.addParam("col", unit.getLocation().getX());
        unitBlock.addParam("row", unit.getLocation().getY());
        unitBlock.addParam("name", unit.getView().getJSONName());
        unitBlock.addParam("civ", unit.getCivilization().getView().getJSONName());
        return unitBlock;
    }

    public StringBuilder getHtmlActions(U unit) {
        StringBuilder attackAction = AttackAction.getHtml(unit);
        StringBuilder captureUnitAction = CaptureUnitAction.getHtml(unit);
        StringBuilder moveUnitAction = MoveUnitAction.getHtml(unit);
        StringBuilder destroyUnitAction = DestroyUnitAction.getHtml(unit);
        if (attackAction == null && captureUnitAction == null && moveUnitAction == null && destroyUnitAction == null) {
            return null;
        }

        return Format.text(
            "<tr>$attackAction</tr>" +
            "<tr>$captureUnitAction</tr>" +
            "<tr>$moveUnitAction</tr>" +
            "<tr>$destroyUnitAction</tr>",

            "$attackAction", attackAction,
            "$captureUnitAction", captureUnitAction,
            "$moveUnitAction", moveUnitAction,
            "$destroyUnitAction", destroyUnitAction
        );
    }
}
