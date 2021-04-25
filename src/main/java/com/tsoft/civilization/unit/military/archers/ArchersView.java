package com.tsoft.civilization.unit.military.archers;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import lombok.Getter;

public class ArchersView extends AbstractUnitView {

    @Getter
    private final L10n name = L10nUnit.ARCHERS_NAME;

    @Override
    public String getLocalizedName() {
        return name.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.ARCHERS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "Archers";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/archer.png";
    }
}
