package com.tsoft.civilization.web.view.building;

import com.tsoft.civilization.L10n.building.L10nMonument;

public class MonumentView extends AbstractBuildingView {
    @Override
    public String getLocalizedName() {
        return L10nMonument.NAME.getLocalized();
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
