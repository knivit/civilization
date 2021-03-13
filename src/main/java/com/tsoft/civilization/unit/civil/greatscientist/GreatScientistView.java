package com.tsoft.civilization.unit.civil.greatscientist;

import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;

public class GreatScientistView extends AbstractUnitView {
    @Override
    public String getLocalizedName() {
        return L10nUnit.GREAT_SCIENTIST_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.GREAT_SCIENTIST_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "GreatScientist";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/great_scientist.png";
    }
}