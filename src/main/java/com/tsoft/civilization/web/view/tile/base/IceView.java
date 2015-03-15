package com.tsoft.civilization.web.view.tile.base;

import com.tsoft.civilization.L10n.L10nTile;

public class IceView extends AbstractTileView {
    @Override
    public String getLocalizedName() {
        return L10nTile.ICE_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.ICE_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "i";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/ice.jpg";
    }
}
