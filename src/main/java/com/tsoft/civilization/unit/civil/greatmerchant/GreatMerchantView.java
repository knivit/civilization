package com.tsoft.civilization.unit.civil.greatmerchant;

import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;

public class GreatMerchantView extends AbstractUnitView {
    @Override
    public String getLocalizedName() {
        return L10nUnit.GREAT_MERCHANT_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.GREAT_MERCHANT_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "GreatMerchant";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/great_merchant.png";
    }
}
