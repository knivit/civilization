package com.tsoft.civilization.web.view.building;

import com.tsoft.civilization.L10n.building.L10nSettlement;
import com.tsoft.civilization.building.settlement.Settlement;

public class SettlementView extends AbstractBuildingView<Settlement> {
    @Override
    public String getLocalizedName() {
        return L10nSettlement.NAME.getLocalized();
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
