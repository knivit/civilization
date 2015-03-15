package com.tsoft.civilization.web.view.tile.base;

import com.tsoft.civilization.L10n.L10nTile;

public class MountainView extends AbstractTileView {
    @Override
    public String getLocalizedName() {
        return L10nTile.MOUNTAIN_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.MOUNTAIN_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "M";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/mountain.jpg";
    }
}
