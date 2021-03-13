package com.tsoft.civilization.improvement.farm;

import com.tsoft.civilization.improvement.L10nImprovement;
import com.tsoft.civilization.improvement.AbstractImprovementView;

public class FarmView extends AbstractImprovementView {
    @Override
    public String getLocalizedName() {
        return L10nImprovement.FARM_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nImprovement.FARM_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/improvements/farm.png";
    }
}
