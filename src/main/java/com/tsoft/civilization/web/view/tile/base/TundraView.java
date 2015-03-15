package com.tsoft.civilization.web.view.tile.base;

import com.tsoft.civilization.L10n.L10nTile;

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
    public String getJSONName() {
        return "t";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/tundra.jpg";
    }
}
