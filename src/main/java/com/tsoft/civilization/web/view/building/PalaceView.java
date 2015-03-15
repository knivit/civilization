package com.tsoft.civilization.web.view.building;

import com.tsoft.civilization.L10n.building.L10nPalace;

public class PalaceView extends AbstractBuildingView {
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
