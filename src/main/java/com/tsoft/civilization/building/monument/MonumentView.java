package com.tsoft.civilization.building.monument;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractBuildingView;
import lombok.Getter;

public class MonumentView extends AbstractBuildingView {

    @Getter
    public final L10n name = L10nMonument.NAME;

    @Override
    public String getLocalizedName() {
        return name.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nMonument.DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/buildings/monument.png";
    }
}
