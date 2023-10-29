package com.tsoft.civilization.unit.catalog.archers;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import lombok.Getter;

public class ArchersView  {

    @Getter
    private final L10n name = L10nUnit.ARCHERS_NAME;

    public String getLocalizedName() {
        return name.getLocalized();
    }

    public String getLocalizedDescription() {
        return L10nUnit.ARCHERS_DESCRIPTION.getLocalized();
    }

    public String getJsonName() {
        return "Archers";
    }

    public String getStatusImageSrc() {
        return "images/status/units/archer.png";
    }
}
