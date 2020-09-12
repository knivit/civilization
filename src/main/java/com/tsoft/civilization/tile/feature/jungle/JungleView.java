package com.tsoft.civilization.tile.feature.jungle;

import com.tsoft.civilization.L10n.L10nFeature;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

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
