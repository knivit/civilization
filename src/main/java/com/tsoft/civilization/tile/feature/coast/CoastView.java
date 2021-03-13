package com.tsoft.civilization.tile.feature.coast;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public class CoastView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nTile.COAST_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.COAST_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return ",";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/coast.jpg";
    }
}
