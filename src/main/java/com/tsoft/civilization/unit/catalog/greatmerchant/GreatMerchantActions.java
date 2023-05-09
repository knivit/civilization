package com.tsoft.civilization.unit.catalog.greatmerchant;

import com.tsoft.civilization.action.AbstractAction;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.catalog.greatmerchant.action.CustomsHouseImprovementAction;
import com.tsoft.civilization.unit.catalog.greatmerchant.action.TradeMissionAction;
import com.tsoft.civilization.util.Format;

public class GreatMerchantActions implements AbstractAction<GreatMerchant> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(GreatMerchant unit) {
        return Format.text("""
            <tr id='actions_table_row'>$customsHouseImprovementAction</tr>
            <tr id='actions_table_row'>$tradeMissionAction</tr>
            $defaultActions
            """,

            "$customsHouseImprovementAction", CustomsHouseImprovementAction.getHtml(unit),
            "$tradeMissionAction", TradeMissionAction.getHtml(unit),
            "$defaultActions", defaultActions.getHtmlActions(unit)
        );
    }
}
