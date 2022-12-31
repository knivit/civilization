package com.tsoft.civilization.tile.improvement;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
public abstract class AbstractImprovement {

    @Getter
    private final String id = UUID.randomUUID().toString();

    @Getter
    private final AbstractTerrain tile;

    @Getter
    private City city;

    public abstract boolean acceptEraAndTechnology(Civilization civilization);
    public abstract boolean acceptTile(AbstractTerrain tile);
    public abstract Supply getSupply();

    public abstract AbstractImprovementView getView();

    protected AbstractImprovement(AbstractTerrain tile) {
        this.tile = tile;
    }

    protected void init(City city) {
        tile.setImprovement(this);
        this.city = city;
    }

    public boolean isBlockingTileSupply() {
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
            "name=" + ((getView() == null) ? "null" : getView().getLocalizedName()) +
            ", location=" + tile.getLocation() +
        '}';
    }
}
