package com.tsoft.civilization.tile.feature.marsh;

import com.tsoft.civilization.tile.feature.L10nFeature;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public class MarshView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nFeature.MARSH_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nFeature.MARSH_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "m";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/marsh.jpg";
    }
}
