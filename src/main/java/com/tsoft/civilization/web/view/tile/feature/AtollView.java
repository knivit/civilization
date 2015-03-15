package com.tsoft.civilization.web.view.tile.feature;

import com.tsoft.civilization.L10n.L10nFeature;

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
    public String getJSONName() {
        return "a";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/atoll.jpg";
    }
}
