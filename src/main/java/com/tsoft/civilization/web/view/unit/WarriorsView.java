package com.tsoft.civilization.web.view.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.Warriors;

public class WarriorsView extends AbstractUnitView<Warriors> {
    @Override
    public String getLocalizedName() {
        return L10nUnit.WARRIORS_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.WARRIORS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "Warriors";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/warrior.png";
    }
}