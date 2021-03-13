package com.tsoft.civilization.improvement.road;

import com.tsoft.civilization.improvement.L10nImprovement;
import com.tsoft.civilization.improvement.AbstractImprovementView;

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
        return "images/status/improvements/road.png";
    }
}
