package com.tsoft.civilization.building.granary;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractBuildingView;
import lombok.Getter;

public class GranaryView extends AbstractBuildingView {

    @Getter
    public final L10n name = L10nGranary.NAME;

    @Override
    public String getLocalizedName() {
        return name.getLocalized();
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
