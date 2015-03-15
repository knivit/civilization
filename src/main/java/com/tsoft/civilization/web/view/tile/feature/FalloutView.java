package com.tsoft.civilization.web.view.tile.feature;

import com.tsoft.civilization.L10n.L10nFeature;

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
    public String getJSONName() {
        return "F";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/fallout.jpg";
    }
}
