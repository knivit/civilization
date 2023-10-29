package com.tsoft.civilization.unit.catalog.greatgeneral;

import com.tsoft.civilization.action.AbstractAction;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.catalog.greatgeneral.action.CitadelImprovementAction;
import com.tsoft.civilization.unit.catalog.greatgeneral.action.CombatBonusAction;
import com.tsoft.civilization.util.Format;

public class GreatGeneralActions implements AbstractAction<GreatGeneral> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(GreatGeneral unit) {
        return Format.text("""
            <tr id='actions_table_row'>$citadelImprovementAction</tr>
            <tr id='actions_table_row'>$combatBonusAction</tr>
            $defaultActions
            """,

            "$citadelImprovementAction", CitadelImprovementAction.getHtml(unit),
            "$combatBonusAction", CombatBonusAction.getHtml(unit),
            "$defaultActions", defaultActions.getHtmlActions(null)//unit)
        );
    }
}
