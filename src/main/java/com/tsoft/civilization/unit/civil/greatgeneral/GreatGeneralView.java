package com.tsoft.civilization.unit.civil.greatgeneral;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import lombok.Getter;

public class GreatGeneralView extends AbstractUnitView {

    @Getter
    public final L10n name = L10nUnit.GREAT_GENERAL_NAME;

    @Override
    public String getLocalizedName() {
        return name.getLocalized();
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
