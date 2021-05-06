package com.tsoft.civilization.tile.tile.grassland;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.tile.AbstractTileView;

public class GrasslandView extends AbstractTileView {
    @Override
    public String getLocalizedName() {
        return L10nTile.GRASSLAND_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.GRASSLAND_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/grassland_status.png";
    }
}
