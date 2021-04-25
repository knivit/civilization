package com.tsoft.civilization.unit.military.warriors;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import lombok.Getter;

public class WarriorsView extends AbstractUnitView {

    @Getter
    public final L10n name = L10nUnit.WARRIORS_NAME;

    @Override
    public String getLocalizedName() {
        return name.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.WARRIORS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "Warriors";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/warrior.png";
    }
}