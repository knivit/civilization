package com.tsoft.civilization.tile.terrain;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.ResourceCategory;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public abstract class AbstractTerrainView {

    public abstract String getLocalizedName();
    public abstract String getLocalizedDescription();
    public abstract String getStatusImageSrc();

    public JsonBlock getJson(AbstractTerrain tile, Civilization civilization) {
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

        AbstractResource resource = tile.getResource();
        if (resource != null && resource.acceptEraAndTechnology(civilization)) {
            if (ResourceCategory.LUXURY.equals(resource.getCategory())) {
                tileBlock.addParam("luxury", resource.getType().name());
            } else {
                tileBlock.addParam("resource", resource.getType().name());
            }
        }

        return tileBlock;
    }
}
