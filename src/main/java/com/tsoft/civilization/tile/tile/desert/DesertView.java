package com.tsoft.civilization.tile.tile.desert;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.tile.AbstractTileView;

public class DesertView extends AbstractTileView {
    @Override
    public String getLocalizedName() {
        return L10nTile.DESERT_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.DESERT_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/desert.jpg";
    }
}
