package com.tsoft.civilization.tile.base.lake;

import com.tsoft.civilization.L10n.L10nTile;
import com.tsoft.civilization.tile.base.AbstractTileView;

public class LakeView extends AbstractTileView {
    @Override
    public String getLocalizedName() {
        return L10nTile.LAKE_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nTile.LAKE_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "l";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/lake.jpg";
    }
}
