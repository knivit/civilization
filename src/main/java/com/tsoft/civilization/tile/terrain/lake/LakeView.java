package com.tsoft.civilization.tile.terrain.lake;

import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.tile.terrain.AbstractTerrainView;

public class LakeView extends AbstractTerrainView {
    @Override
    public String getLocalizedName() {
        return L10nTile.LAKE_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.LAKE_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/lake_status.png";
    }
}
