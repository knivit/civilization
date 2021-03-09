package com.tsoft.civilization.unit.civil.greatmerchant;

import com.tsoft.civilization.common.AbstractAction;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.civil.greatmerchant.action.CustomsHouseImprovementAction;
import com.tsoft.civilization.unit.civil.greatmerchant.action.TradeMissionAction;
import com.tsoft.civilization.util.Format;

public class GreatMerchantActions implements AbstractAction<GreatMerchant> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(GreatMerchant unit) {
        return Format.text("""
            $defaultActions
            <tr id='actions_table_row'>$customsHouseImprovementAction</tr>
            <tr id='actions_table_row'>$tradeMissionAction</tr>
            """,

            "$defaultActions", defaultActions.getHtmlActions(unit),
            "$customsHouseImprovementAction", CustomsHouseImprovementAction.getHtml(unit),
            "$tradeMissionAction", TradeMissionAction.getHtml(unit)
        );
    }
}
