package com.tsoft.civilization.tile.base.ocean;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.base.AbstractTileView;

public class OceanView extends AbstractTileView {
    @Override
    public String getLocalizedName() {
        return L10nTile.OCEAN_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.OCEAN_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return ".";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/ocean.jpg";
    }
}
