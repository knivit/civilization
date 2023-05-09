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

        if (!tile.getFeatures().isEmpty()) {
            tileBlock.startArray("features");
            tile.getFeatures().forEach(e -> {
                AbstractFeatureView featureView = e.getView();
                tileBlock.addElement(featureView.getJson(e).getText());
            });
            tileBlock.stopArray();
        }

        if (!tile.getImprovements().isEmpty()) {
            tileBlock.startArray("improvements");
            tile.getImprovements().forEach(e -> {
                tileBlock.addElement(e.getClass().getSimpleName());
            });
            tileBlock.stopArray();
        }

        AbstractResource resource = tile.getResource();
        if (resource != null && resource.acceptEraAndTechnology(civilization)) {
            if (ResourceCategory.LUXURY.equals(resource.getCategory())) {
                tileBlock.addParam("luxury", resource.getType().name());
            } else {
                tileBlock.addParam("resource", resource.getType().name());
            }
        }

        if (tile.isDeepOcean()) {
            tileBlock.addParam("isDeepOcean", "true");
        }

        return tileBlock;
    }
}
