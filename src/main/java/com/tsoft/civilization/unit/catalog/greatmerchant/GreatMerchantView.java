package com.tsoft.civilization.unit.catalog.greatmerchant;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import lombok.Getter;

public class GreatMerchantView extends AbstractUnitView {

    @Getter
    public final L10n name = L10nUnit.GREAT_MERCHANT_NAME;

    @Override
    public String getLocalizedName() {
        return name.getLocalized();
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
