package com.tsoft.civilization.web.view.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;

public class ArchersView extends AbstractUnitView {
    @Override
    public String getLocalizedName() {
        return L10nUnit.ARCHERS_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.ARCHERS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "Archers";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/archer.png";
    }
}
