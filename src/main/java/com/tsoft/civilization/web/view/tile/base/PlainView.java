package com.tsoft.civilization.web.view.tile.base;

import com.tsoft.civilization.L10n.L10nTile;

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
    public String getJSONName() {
        return "p";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/plain.jpg";
    }
}
