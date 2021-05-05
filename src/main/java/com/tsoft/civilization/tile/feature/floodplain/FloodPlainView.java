package com.tsoft.civilization.tile.feature.floodplain;

import com.tsoft.civilization.tile.feature.L10nFeature;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public class FloodPlainView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nFeature.FLOODPLAIN_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nFeature.FLOODPLAIN_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/floodplain.jpg";
    }
}
