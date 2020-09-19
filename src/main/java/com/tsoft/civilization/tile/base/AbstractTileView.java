package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public abstract class AbstractTileView {
    public abstract String getLocalizedName();
    public abstract String getLocalizedDescription();
    public abstract String getJSONName();
    public abstract String getStatusImageSrc();

    public JsonBlock getJSON(AbstractTile<?> tile) {
        JsonBlock tileBlock = new JsonBlock();
        tileBlock.addParam("name", tile.getView().getJSONName());

        tileBlock.startArray("features");
        tile.getTerrainFeatures().forEach(feature -> {
            AbstractFeatureView featureView = feature.getView();
            tileBlock.addElement(featureView.getJSON(feature).getText());
        });
        tileBlock.stopArray();
        return tileBlock;
    }
}
