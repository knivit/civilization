package com.tsoft.civilization.web.view.building;

import com.tsoft.civilization.L10n.building.L10nWalls;
import com.tsoft.civilization.building.walls.Walls;

public class WallsView extends AbstractBuildingView<Walls> {
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
