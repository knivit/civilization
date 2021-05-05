package com.tsoft.civilization.tile.tile.ocean;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.tile.AbstractTileView;

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
    public String getStatusImageSrc() {
        return "images/status/tiles/ocean.jpg";
    }
}
