package com.tsoft.civilization.tile.feature.fallout;

import com.tsoft.civilization.tile.feature.L10nFeature;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public class FalloutView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nFeature.FALLOUT_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nFeature.FALLOUT_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/fallout_status.png";
    }
}
