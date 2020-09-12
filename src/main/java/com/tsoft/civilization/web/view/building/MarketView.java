package com.tsoft.civilization.web.view.building;

import com.tsoft.civilization.L10n.building.L10nMarket;
import com.tsoft.civilization.building.market.Market;

public class MarketView extends AbstractBuildingView<Market> {
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
