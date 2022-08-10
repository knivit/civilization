package com.tsoft.civilization.tile.terrain.snow;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.terrain.AbstractTerrainView;

public class SnowView extends AbstractTerrainView {
    @Override
    public String getLocalizedName() {
        return L10nTile.SNOW_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.SNOW_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/snow_status.png";
    }
}
