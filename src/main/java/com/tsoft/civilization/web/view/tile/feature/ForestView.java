package com.tsoft.civilization.web.view.tile.feature;

import com.tsoft.civilization.L10n.L10nFeature;

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
    public String getJSONName() {
        return "f";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/forest.jpg";
    }
}
