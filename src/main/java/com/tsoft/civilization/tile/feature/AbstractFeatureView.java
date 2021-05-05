package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.web.view.JsonBlock;

public abstract class AbstractFeatureView {
    public abstract String getLocalizedName();
    public abstract String getLocalizedDescription();
    public abstract String getStatusImageSrc();

    public JsonBlock getJson(AbstractFeature feature) {
        JsonBlock tileBlock = new JsonBlock();
        tileBlock.addParam("name", feature.getClass().getSimpleName());
        return tileBlock;
    }
}
