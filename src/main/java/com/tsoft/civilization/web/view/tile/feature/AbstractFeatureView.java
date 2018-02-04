package com.tsoft.civilization.web.view.tile.feature;

import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.web.view.JSONBlock;

public abstract class AbstractFeatureView {
    public abstract String getLocalizedName();
    public abstract String getLocalizedDescription();
    public abstract String getJSONName();
    public abstract String getStatusImageSrc();

    public JSONBlock getJSON(TerrainFeature feature) {
        JSONBlock tileBlock = new JSONBlock();
        tileBlock.addParam("name", feature.getView().getJSONName());
        return tileBlock;
    }
}
