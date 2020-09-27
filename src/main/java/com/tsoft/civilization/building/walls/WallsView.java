package com.tsoft.civilization.building.walls;

import com.tsoft.civilization.L10n.building.L10nWalls;
import com.tsoft.civilization.building.AbstractBuildingView;

public class WallsView extends AbstractBuildingView {
    @Override
    public String getLocalizedName() {
        return L10nWalls.NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nWalls.DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/buildings/walls.png";
    }
}
