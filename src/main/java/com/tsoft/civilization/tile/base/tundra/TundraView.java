package com.tsoft.civilization.tile.base.tundra;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.base.AbstractTileView;

public class TundraView extends AbstractTileView {
    @Override
    public String getLocalizedName() {
        return L10nTile.TUNDRA_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.TUNDRA_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "t";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/tundra.jpg";
    }
}
