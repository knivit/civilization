package com.tsoft.civilization.unit.civil.settlers;

import com.tsoft.civilization.common.AbstractAction;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.civil.settlers.action.BuildCityAction;
import com.tsoft.civilization.util.Format;

public class SettlersActions implements AbstractAction<Settlers> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(Settlers unit) {
        return Format.text("""
            $defaultActions
            <tr id='actions_table_row'>$buildCityAction</tr>
            """,

            "$defaultActions", defaultActions.getHtmlActions(unit),
            "$buildCityAction", BuildCityAction.getHtml(unit)
        );
    }
}
