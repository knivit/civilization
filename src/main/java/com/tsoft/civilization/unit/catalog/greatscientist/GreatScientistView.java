package com.tsoft.civilization.unit.catalog.greatscientist;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import lombok.Getter;

public class GreatScientistView {

    @Getter
    public final L10n name = L10nUnit.GREAT_SCIENTIST_NAME;

    public String getLocalizedName() {
        return name.getLocalized();
    }

    public String getLocalizedDescription() {
        return L10nUnit.GREAT_SCIENTIST_DESCRIPTION.getLocalized();
    }

    public String getJsonName() {
        return "GreatScientist";
    }

    public String getStatusImageSrc() {
        return "images/status/units/great_scientist.png";
    }
}