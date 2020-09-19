package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.web.view.JsonBlock;

public abstract class AbstractFeatureView {
    public abstract String getLocalizedName();
    public abstract String getLocalizedDescription();
    public abstract String getJSONName();
    public abstract String getStatusImageSrc();

    public JsonBlock getJSON(TerrainFeature<?> feature) {
        JsonBlock tileBlock = new JsonBlock();
        tileBlock.addParam("name", feature.getView().getJSONName());
        return tileBlock;
    }
}
