package com.tsoft.civilization.building.palace;

import com.tsoft.civilization.L10n.building.L10nPalace;
import com.tsoft.civilization.building.AbstractBuildingView;

public class PalaceView extends AbstractBuildingView<Palace> {
    @Override
    public String getLocalizedName() {
        return L10nPalace.NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nPalace.DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/buildings/palace.png";
    }
}
