package com.tsoft.civilization.building.market;

import com.tsoft.civilization.L10n.building.L10nMarket;
import com.tsoft.civilization.building.AbstractBuildingView;

public class MarketView extends AbstractBuildingView {
    @Override
    public String getLocalizedName() {
        return L10nMarket.NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nMarket.DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/buildings/market.png";
    }
}
