package com.tsoft.civilization.unit.civil.workers;

import com.tsoft.civilization.action.AbstractAction;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.civil.workers.action.BuildFarmAction;
import com.tsoft.civilization.unit.civil.workers.action.RemoveForestAction;
import com.tsoft.civilization.unit.civil.workers.action.RemoveHillAction;
import com.tsoft.civilization.util.Format;

public class WorkersActions implements AbstractAction<Workers> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(Workers unit) {
        return Format.text("""
            $defaultActions
            <tr id='actions_table_row'>$buildFarmAction</tr>
            <tr id='actions_table_row'>$removeForestAction</tr>
            <tr id='actions_table_row'>$removeHillAction</tr>
            """,

            "$defaultActions", defaultActions.getHtmlActions(unit),
            "$buildFarmAction", BuildFarmAction.getHtml(unit),
            "$removeForestAction", RemoveForestAction.getHtml(unit),
            "$removeHillAction", RemoveHillAction.getHtml(unit)
        );
    }
}
