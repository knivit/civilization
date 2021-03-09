package com.tsoft.civilization.tile.feature.mountain;

import com.tsoft.civilization.L10n.L10nTile;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public class MountainView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nTile.MOUNTAIN_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.MOUNTAIN_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "M";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/mountain.jpg";
    }
}
