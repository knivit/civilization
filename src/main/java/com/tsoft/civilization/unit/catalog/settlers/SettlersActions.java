package com.tsoft.civilization.unit.catalog.settlers;

import com.tsoft.civilization.action.AbstractAction;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.catalog.settlers.action.BuildCityAction;
import com.tsoft.civilization.util.Format;

public class SettlersActions implements AbstractAction<AbstractUnit> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(AbstractUnit unit) {
        return Format.text("""
            <tr id='actions_table_row'>$buildCityAction</tr>
            $defaultActions
            """,

            "$buildCityAction", BuildCityAction.getHtml(unit),
            "$defaultActions", defaultActions.getHtmlActions(unit)
        );
    }
}
