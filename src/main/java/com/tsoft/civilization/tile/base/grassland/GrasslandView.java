package com.tsoft.civilization.tile.base.grassland;

import com.tsoft.civilization.L10n.L10nTile;
import com.tsoft.civilization.tile.base.AbstractTileView;

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
    public String getJSONName() {
        return "g";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/grassland.jpg";
    }
}
