package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Format;

public class DefaultUnitActions {

    public StringBuilder getHtmlActions(AbstractUnit unit) {
        StringBuilder attackAction = AttackAction.getHtml(unit);
        StringBuilder captureUnitAction = CaptureUnitAction.getHtml(unit);
        StringBuilder moveUnitAction = MoveUnitAction.getHtml(unit);
        StringBuilder destroyUnitAction = DestroyUnitAction.getHtml(unit);

        if (attackAction == null && captureUnitAction == null && moveUnitAction == null && destroyUnitAction == null) {
            return null;
        }

        return Format.text("""
            <tr>$attackAction</tr>
            <tr>$captureUnitAction</tr>
            <tr>$moveUnitAction</tr>
            <tr>$destroyUnitAction</tr>
            """,

            "$attackAction", attackAction,
            "$captureUnitAction", captureUnitAction,
            "$moveUnitAction", moveUnitAction,
            "$destroyUnitAction", destroyUnitAction
        );
    }
}
