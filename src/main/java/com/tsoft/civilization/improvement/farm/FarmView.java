package com.tsoft.civilization.improvement.farm;

import com.tsoft.civilization.L10n.L10nImprovement;
import com.tsoft.civilization.improvement.AbstractImprovementView;

public class FarmView extends AbstractImprovementView {
    @Override
    public String getLocalizedName() {
        return L10nImprovement.ROAD_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nImprovement.FARM_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "";
    }
}
