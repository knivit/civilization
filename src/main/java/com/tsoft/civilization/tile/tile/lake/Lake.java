package com.tsoft.civilization.tile.tile.lake;

import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileType;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Lake: A body of water inside a landmass. It consists of one to ten water tiles that are enclosed by land tiles on all sides.
 * Note that if more water tiles are enclosed (including some ocean tiles), they are considered an "inner sea" and not a lake!
 * Basic Production: 2 Food, 1 Gold
 * Movement Cost: 1
 */
public class Lake extends AbstractTile {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final LakeView VIEW = new LakeView();

    @Getter
    private final boolean canBuildCity = false;

    @Override
    public TileType getTileType() {
        return TileType.SEA;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(2).gold(1).build();
    }

    @Override
    public int getDefensiveBonusPercent() {
        return 0;
    }

    @Override
    public LakeView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
