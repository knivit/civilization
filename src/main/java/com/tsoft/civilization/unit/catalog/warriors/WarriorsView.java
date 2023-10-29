package com.tsoft.civilization.unit.catalog.warriors;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import lombok.Getter;

public class WarriorsView {

    @Getter
    public final L10n name = L10nUnit.WARRIORS_NAME;

    public String getLocalizedName() {
        return name.getLocalized();
    }

    public String getLocalizedDescription() {
        return L10nUnit.WARRIORS_DESCRIPTION.getLocalized();
    }

    public String getJsonName() {
        return "Warriors";
    }

    public String getStatusImageSrc() {
        return "images/status/units/warrior.png";
    }
}