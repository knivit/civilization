package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.combat.action.PillageAction;
import com.tsoft.civilization.combat.service.AttackService;
import com.tsoft.civilization.combat.action.AttackAction;
import com.tsoft.civilization.combat.action.CaptureUnitAction;
import com.tsoft.civilization.combat.service.PillageService;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.combat.service.CaptureUnitService;
import com.tsoft.civilization.unit.service.destroy.DestroyUnitService;
import com.tsoft.civilization.unit.service.move.MoveUnitService;
import com.tsoft.civilization.util.Format;

public class DefaultUnitActions {

    private final AttackService attackService = new AttackService();
    private final AttackAction attackAction = new AttackAction(attackService);

    private final PillageService pillageService = new PillageService();
    private final PillageAction pillageAction = new PillageAction(pillageService);

    private final MoveUnitService moveUnitService = new MoveUnitService();
    private final MoveUnitAction moveAction = new MoveUnitAction(moveUnitService);

    private final CaptureUnitService captureUnitService = new CaptureUnitService();
    private final CaptureUnitAction captureAction = new CaptureUnitAction(captureUnitService);

    private final DestroyUnitService destroyService = new DestroyUnitService();
    private final DestroyUnitAction destroyAction = new DestroyUnitAction(destroyService);

    public StringBuilder getHtmlActions(AbstractUnit unit) {
        StringBuilder attackUnitAction = attackAction.getHtml(unit);
        StringBuilder pillageUnitAction = pillageAction.getHtml(unit);
        StringBuilder captureUnitAction = captureAction.getHtml(unit);
        StringBuilder moveUnitAction = moveAction.getHtml(unit);
        StringBuilder destroyUnitAction = destroyAction.getHtml(unit);

        if (attackUnitAction == null && captureUnitAction == null && moveUnitAction == null && destroyUnitAction == null) {
            return null;
        }

        return Format.text("""
            <tr>$attackUnitAction</tr>
            <tr>$pillageUnitAction</tr>
            <tr>$captureUnitAction</tr>
            <tr>$moveUnitAction</tr>
            <tr>$destroyUnitAction</tr>
            """,

            "$attackUnitAction", attackUnitAction,
            "$pillageUnitAction", pillageUnitAction,
            "$captureUnitAction", captureUnitAction,
            "$moveUnitAction", moveUnitAction,
            "$destroyUnitAction", destroyUnitAction
        );
    }
}
