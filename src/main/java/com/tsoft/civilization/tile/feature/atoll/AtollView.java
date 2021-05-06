package com.tsoft.civilization.tile.feature.atoll;

import com.tsoft.civilization.tile.feature.L10nFeature;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public class AtollView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nFeature.ATOLL_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nFeature.ATOLL_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/atoll_status.png";
    }
}
