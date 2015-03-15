package com.tsoft.civilization.web.view.tile.feature;

import com.tsoft.civilization.L10n.L10nFeature;

public class HillView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nFeature.HILL_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nFeature.HILL_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "h";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/hill.jpg";
    }
}
