package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.combat.CombatService;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Format;

public class DefaultUnitActions {

    private final CombatService combatService = new CombatService();
    private final AttackAction attackAction = new AttackAction(combatService);

    public StringBuilder getHtmlActions(AbstractUnit unit) {
        StringBuilder attackUnitAction = attackAction.getHtml(unit);
        StringBuilder captureUnitAction = CaptureUnitAction.getHtml(unit);
        StringBuilder moveUnitAction = MoveUnitAction.getHtml(unit);
        StringBuilder destroyUnitAction = DestroyUnitAction.getHtml(unit);

        if (attackUnitAction == null && captureUnitAction == null && moveUnitAction == null && destroyUnitAction == null) {
            return null;
        }

        return Format.text("""
            <tr>$attackAction</tr>
            <tr>$captureUnitAction</tr>
            <tr>$moveUnitAction</tr>
            <tr>$destroyUnitAction</tr>
            """,

            "$attackAction", attackUnitAction,
            "$captureUnitAction", captureUnitAction,
            "$moveUnitAction", moveUnitAction,
            "$destroyUnitAction", destroyUnitAction
        );
    }
}
