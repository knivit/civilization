package com.tsoft.civilization.tile.feature.oasis;

import com.tsoft.civilization.tile.feature.L10nFeature;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public class OasisView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nFeature.OASIS_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nFeature.OASIS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/oasis.jpg";
    }
}
