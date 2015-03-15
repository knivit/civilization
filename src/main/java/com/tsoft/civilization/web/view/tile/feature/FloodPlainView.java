package com.tsoft.civilization.web.view.tile.feature;

import com.tsoft.civilization.L10n.L10nFeature;

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
    public String getJSONName() {
        return "n";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/floodplain.jpg";
    }
}
