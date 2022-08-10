package com.tsoft.civilization.tile.terrain.ocean;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.terrain.AbstractTerrainView;

public class OceanView extends AbstractTerrainView {
    @Override
    public String getLocalizedName() {
        return L10nTile.OCEAN_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.OCEAN_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/ocean_status.png";
    }
}
