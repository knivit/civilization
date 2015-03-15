package com.tsoft.civilization.web.view.tile.base;

import com.tsoft.civilization.L10n.L10nTile;

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
    public String getJSONName() {
        return "d";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/desert.jpg";
    }
}
