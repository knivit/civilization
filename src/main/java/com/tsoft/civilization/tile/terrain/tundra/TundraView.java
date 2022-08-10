package com.tsoft.civilization.tile.terrain.tundra;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.terrain.AbstractTerrainView;

public class TundraView extends AbstractTerrainView {
    @Override
    public String getLocalizedName() {
        return L10nTile.TUNDRA_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.TUNDRA_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/tundra_status.png";
    }
}
