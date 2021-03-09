package com.tsoft.civilization.unit.civil.settlers;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;

public class SettlersView extends AbstractUnitView {
    @Override
    public String getLocalizedName() {
        return L10nUnit.SETTLERS_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.SETTLERS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "Settlers";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/settler.png";
    }
}
