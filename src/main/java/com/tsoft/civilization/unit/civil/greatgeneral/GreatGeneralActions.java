package com.tsoft.civilization.unit.civil.greatgeneral;

import com.tsoft.civilization.common.AbstractAction;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.civil.greatgeneral.action.CitadelImprovementAction;
import com.tsoft.civilization.unit.civil.greatgeneral.action.CombatBonusAction;
import com.tsoft.civilization.util.Format;

public class GreatGeneralActions implements AbstractAction<GreatGeneral> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(GreatGeneral unit) {
        return Format.text("""
            $commonActions
            <tr id='actions_table_row'>$citadelImprovementAction</tr>
            <tr id='actions_table_row'>$combatBonusAction</tr>
            """,

            "$defaultActions", defaultActions.getHtmlActions(unit),
            "$citadelImprovementAction", CitadelImprovementAction.getHtml(unit),
            "$combatBonusAction", CombatBonusAction.getHtml(unit)
        );
    }
}
