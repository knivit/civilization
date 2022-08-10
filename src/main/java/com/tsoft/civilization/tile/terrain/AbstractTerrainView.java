package com.tsoft.civilization.tile.terrain;

import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public abstract class AbstractTerrainView {
    public abstract String getLocalizedName();
    public abstract String getLocalizedDescription();
    public abstract String getStatusImageSrc();

    public JsonBlock getJson(AbstractTerrain tile) {
        JsonBlock tileBlock = new JsonBlock();
        tileBlock.addParam("name", tile.getClass().getSimpleName());

        tileBlock.startArray("features");
        tile.getFeatures().forEach(feature -> {
            AbstractFeatureView featureView = feature.getView();
            tileBlock.addElement(featureView.getJson(feature).getText());
        });
        tileBlock.stopArray();

        if (tile.getImprovement() != null) {
            tileBlock.addParam("improvement", tile.getImprovement().getClass().getSimpleName());
        }

        if (tile.getResource() != null) {
            tileBlock.addParam("resource", tile.getResource().getClass().getSimpleName());
        }

        if (tile.getLuxury() != null) {
            tileBlock.addParam("luxury", tile.getLuxury().getClass().getSimpleName());
        }

        return tileBlock;
    }
}
