package com.tsoft.civilization.web.view.tile.base;

import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.web.view.JSONBlock;
import com.tsoft.civilization.web.view.tile.feature.AbstractFeatureView;

public abstract class AbstractTileView {
    public abstract String getLocalizedName();
    public abstract String getLocalizedDescription();
    public abstract String getJSONName();
    public abstract String getStatusImageSrc();

    public JSONBlock getJSON(AbstractTile tile) {
        JSONBlock tileBlock = new JSONBlock();
        tileBlock.addParam("name", tile.getView().getJSONName());

        tileBlock.startArray("features");
        for (int i = 0; i < tile.getTerrainFeatures().size(); i ++) {
            TerrainFeature feature = tile.getTerrainFeatures().get(i);
            AbstractFeatureView featureView = feature.getView();
            tileBlock.addElement(featureView.getJSON(feature).getText());
        }
        tileBlock.stopArray();
        return tileBlock;
    }
}
