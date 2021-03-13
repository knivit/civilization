package com.tsoft.civilization.tile.base.plain;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.base.AbstractTileView;

public class PlainView extends AbstractTileView {
    @Override
    public String getLocalizedName() {
        return L10nTile.PLAIN_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.PLAIN_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "p";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/plain.jpg";
    }
}
