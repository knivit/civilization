package com.tsoft.civilization.tile.feature.hill;

import com.tsoft.civilization.tile.feature.L10nFeature;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

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
    public String getStatusImageSrc() {
        return "images/status/tiles/hill.jpg";
    }
}
