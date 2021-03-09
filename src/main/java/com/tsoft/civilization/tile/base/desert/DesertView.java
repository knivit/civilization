package com.tsoft.civilization.tile.base.desert;

import com.tsoft.civilization.L10n.L10nTile;
import com.tsoft.civilization.tile.base.AbstractTileView;

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
    public String getJsonName() {
        return "d";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/desert.jpg";
    }
}
