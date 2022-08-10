package com.tsoft.civilization.tile.terrain.desert;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.terrain.AbstractTerrainView;

public class DesertView extends AbstractTerrainView {
    @Override
    public String getLocalizedName() {
        return L10nTile.DESERT_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.DESERT_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/desert_status.png";
    }
}
