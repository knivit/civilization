package com.tsoft.civilization.tile.terrain.plains;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.terrain.AbstractTerrainView;

public class PlainsView extends AbstractTerrainView {
    @Override
    public String getLocalizedName() {
        return L10nTile.PLAIN_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.PLAIN_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/plain_status.png";
    }
}
