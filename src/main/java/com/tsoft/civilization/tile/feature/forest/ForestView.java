package com.tsoft.civilization.tile.feature.forest;

import com.tsoft.civilization.tile.feature.L10nFeature;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public class ForestView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nFeature.FOREST_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nFeature.FOREST_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/forest_status.png";
    }
}
