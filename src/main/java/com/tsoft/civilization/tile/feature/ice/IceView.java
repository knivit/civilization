package com.tsoft.civilization.tile.feature.ice;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public class IceView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nTile.ICE_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.ICE_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/ice_status.png";
    }
}
