package com.tsoft.civilization.web.view.tile.base;

import com.tsoft.civilization.L10n.L10nTile;

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
