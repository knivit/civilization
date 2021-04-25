package com.tsoft.civilization.building.palace;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractBuildingView;
import lombok.Getter;

public class PalaceView extends AbstractBuildingView {

    @Getter
    public final L10n name = L10nPalace.NAME;

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
