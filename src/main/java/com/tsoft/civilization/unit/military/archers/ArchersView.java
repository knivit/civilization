package com.tsoft.civilization.unit.military.archers;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;

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
