package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.TileSupply;
import com.tsoft.civilization.web.view.tile.base.CoastView;

import java.util.UUID;

/**
 * Coastal tiles now often go several hexes out into the water,
 * allowing for better freedom of movement for ancient units that are restricted from deep water
 */
public class Coast extends AbstractTile<CoastView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final CoastView VIEW = new CoastView();

    @Override
    public TileType getTileType() {
        return TileType.SEA;
    }

    @Override
    public TileSupply getBaseSupply() {
        return new TileSupply(0, 0, 1);
    }

    @Override
    public boolean canBuildCity() {
        return false;
    }

    @Override
    public int getDefensiveBonusPercent() {
        return 0;
    }

    @Override
    public CoastView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
