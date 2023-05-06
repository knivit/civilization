package com.tsoft.civilization.unit.catalog.greatengineer;

import com.tsoft.civilization.action.AbstractAction;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.catalog.greatengineer.action.HurryProductionAction;
import com.tsoft.civilization.unit.catalog.greatengineer.action.ManufactureImprovementAction;
import com.tsoft.civilization.util.Format;

public class GreatEngineerActions implements AbstractAction<GreatEngineer> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(GreatEngineer unit) {
        return Format.text("""
            $defaultActions
            <tr id='actions_table_row'>$manufactureImprovementAction</tr>
            <tr id='actions_table_row'>$hurryProductionAction</tr>
            """,

            "$manufactureImprovementAction", ManufactureImprovementAction.getHtml(unit),
            "$hurryProductionAction", HurryProductionAction.getHtml(unit),
            "$defaultActions", defaultActions.getHtmlActions(unit)
        );
    }
}
