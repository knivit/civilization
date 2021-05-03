package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.combat.CombatService;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.combat.CaptureService;
import com.tsoft.civilization.unit.move.UnitMoveService;
import com.tsoft.civilization.util.Format;

public class DefaultUnitActions {

    private final CombatService combatService = new CombatService();
    private final AttackAction attackAction = new AttackAction(combatService);

    private final UnitMoveService unitMoveService = new UnitMoveService();
    private final MoveUnitAction moveAction = new MoveUnitAction(unitMoveService);

    private final CaptureService captureService = new CaptureService();
    private final CaptureUnitAction captureAction = new CaptureUnitAction(captureService);

    public StringBuilder getHtmlActions(AbstractUnit unit) {
        StringBuilder attackUnitAction = attackAction.getHtml(unit);
        StringBuilder captureUnitAction = captureAction.getHtml(unit);
        StringBuilder moveUnitAction = moveAction.getHtml(unit);
        StringBuilder destroyUnitAction = DestroyUnitAction.getHtml(unit);

        if (attackUnitAction == null && captureUnitAction == null && moveUnitAction == null && destroyUnitAction == null) {
            return null;
        }

        return Format.text("""
            <tr>$attackUnitAction</tr>
            <tr>$captureUnitAction</tr>
            <tr>$moveUnitAction</tr>
            <tr>$destroyUnitAction</tr>
            """,

            "$attackUnitAction", attackUnitAction,
            "$captureUnitAction", captureUnitAction,
            "$moveUnitAction", moveUnitAction,
            "$destroyUnitAction", destroyUnitAction
        );
    }
}
