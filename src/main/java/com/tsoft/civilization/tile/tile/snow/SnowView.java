package com.tsoft.civilization.tile.tile.snow;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.tile.AbstractTileView;

public class SnowView extends AbstractTileView {
    @Override
    public String getLocalizedName() {
        return L10nTile.SNOW_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.SNOW_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/snow_status.png";
    }
}
