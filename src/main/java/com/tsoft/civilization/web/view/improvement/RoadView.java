package com.tsoft.civilization.web.view.improvement;

import com.tsoft.civilization.L10n.L10nImprovement;

public class RoadView extends AbstractImprovementView {
    @Override
    public String getLocalizedName() {
        return L10nImprovement.ROAD_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nImprovement.ROAD_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "";
    }
}
