package com.tsoft.civilization.building.granary;

import com.tsoft.civilization.L10n.building.L10nGranary;
import com.tsoft.civilization.building.AbstractBuildingView;

public class GranaryView extends AbstractBuildingView<Granary> {
    @Override
    public String getLocalizedName() {
        return L10nGranary.NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nGranary.DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/buildings/granary.png";
    }
}
