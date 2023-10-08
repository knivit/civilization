package com.unciv.logic.map.tile;

public class TileHistory implements IsPartOfGameInfoSerialization {

    public TileHistoryState(String owningCivName) {
        this(owningCivName, CityCenterType.None);
    }

    public TileHistoryState(Tile tile) {

    }

    public TileHistoryState(
            /** The name of the civilization owning this tile or `null` if there is no owner. */
            String owningCivName,
            /** `null` if this tile does not have a city center. Otherwise this field denotes of which type this city center is. */
            CityCenterType cityCenterType
    ) {

        constructor(tile: Tile) : this(
                tile.getOwner()?.civName,
                when {
            !tile.isCityCenter() -> CityCenterType.None
            tile.getCity()?.isCapital() == true -> CityCenterType.Capital
                else -> CityCenterType.Regular
        }
        )
    }
}
