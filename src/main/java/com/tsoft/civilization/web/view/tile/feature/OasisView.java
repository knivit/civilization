package com.tsoft.civilization.web.view.tile.feature;

import com.tsoft.civilization.L10n.L10nFeature;

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
    public String getJSONName() {
        return "o";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/oasis.jpg";
    }
}
