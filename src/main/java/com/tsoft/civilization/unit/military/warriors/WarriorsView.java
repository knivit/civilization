package com.tsoft.civilization.unit.military.warriors;

import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;

public class WarriorsView extends AbstractUnitView {
    @Override
    public String getLocalizedName() {
        return L10nUnit.WARRIORS_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.WARRIORS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "Warriors";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/warrior.png";
    }
}