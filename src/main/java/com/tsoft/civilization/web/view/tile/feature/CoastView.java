package com.tsoft.civilization.web.view.tile.feature;

import com.tsoft.civilization.L10n.L10nTile;

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
    public String getJSONName() {
        return ",";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/coast.jpg";
    }
}
