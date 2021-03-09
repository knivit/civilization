package com.tsoft.civilization.unit.civil.greatengineer;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;

public class GreatEngineerView extends AbstractUnitView {
    @Override
    public String getLocalizedName() {
        return L10nUnit.GREAT_ENGINEER_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.GREAT_ENGINEER_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "GreatEngineer";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/great_engineer.png";
    }
}
