package com.tsoft.civilization.tile.base.snow;

import com.tsoft.civilization.L10n.L10nTile;
import com.tsoft.civilization.tile.base.AbstractTileView;

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
    public String getJsonName() {
        return "s";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/snow.jpg";
    }
}
