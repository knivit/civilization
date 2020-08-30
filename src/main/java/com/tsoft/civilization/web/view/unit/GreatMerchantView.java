package com.tsoft.civilization.web.view.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.unit.greatmerchant.CustomsHouseImprovementAction;
import com.tsoft.civilization.action.unit.greatmerchant.TradeMissionAction;
import com.tsoft.civilization.unit.civil.GreatMerchant;
import com.tsoft.civilization.util.Format;

public class GreatMerchantView extends AbstractUnitView<GreatMerchant> {
    @Override
    public String getLocalizedName() {
        return L10nUnit.GREAT_MERCHANT_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.GREAT_MERCHANT_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "GreatMerchant";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/great_merchant.png";
    }

    @Override
    public StringBuilder getHtmlActions(GreatMerchant unit) {
        return Format.text(
            "$commonActions" +
            "<tr id='actions_table_row'>$customsHouseImprovementAction</tr>" +
            "<tr id='actions_table_row'>$tradeMissionAction</tr>",

            "$commonActions", super.getHtmlActions(unit),
            "$customsHouseImprovementAction", CustomsHouseImprovementAction.getHtml(unit),
            "$tradeMissionAction", TradeMissionAction.getHtml(unit)
        );
    }
}
