package com.tsoft.civilization.building.settlement;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractBuildingView;
import lombok.Getter;

public class SettlementView extends AbstractBuildingView {

    @Getter
    public final L10n name = L10nSettlement.NAME;

    @Override
    public String getLocalizedName() {
        return name.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nSettlement.DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/buildings/settlement.png";
    }
}
