package com.tsoft.civilization.web.view.tile.feature;

import com.tsoft.civilization.L10n.L10nFeature;

public class JungleView extends AbstractFeatureView {
    @Override
    public String getLocalizedName() {
        return L10nFeature.JUNGLE_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nFeature.JUNGLE_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "j";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/jungle.jpg";
    }
}
