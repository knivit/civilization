package com.tsoft.civilization.unit.civil.greatgeneral;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;

public class GreatGeneralView extends AbstractUnitView {
    @Override
    public String getLocalizedName() {
        return L10nUnit.GREAT_GENERAL_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.GREAT_GENERAL_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "GreatGeneral";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/great_general.png";
    }
}
