package com.tsoft.civilization.improvement;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
public abstract class AbstractImprovement implements HasSupply {

    @Getter
    private final String id = UUID.randomUUID().toString();

    @Getter
    private final AbstractTerrain tile;

    @Getter
    private City city;

    public abstract String getClassUuid();
    public abstract ImprovementBaseState getBaseState();
    public abstract AbstractImprovementView getView();
    public abstract boolean acceptEraAndTechnology(Civilization civilization);
    public abstract boolean acceptTile(AbstractTerrain tile);

    protected AbstractImprovement(AbstractTerrain tile) {
        this.tile = tile;
    }

    protected void init(City city) {
        tile.setImprovement(this);
        this.city = city;
    }

    @Override
    public Supply getBaseSupply(Civilization civilization) {
        Supply baseSupply = getBaseState().getSupply();
        double modifier = ImprovementBaseModifiers.getModifier(civilization);

        return Supply.builder()
            .food((int)Math.round(baseSupply.getFood() * modifier))
            .production((int)Math.round(baseSupply.getProduction() * modifier))
            .gold((int)Math.round(baseSupply.getGold() * modifier))
            .science((int)Math.round(baseSupply.getScience() * modifier))
            .culture((int)Math.round(baseSupply.getCulture() * modifier))
            .faith((int)Math.round(baseSupply.getFaith() * modifier))
            .tourism((int)Math.round(baseSupply.getTourism() * modifier))
            .greatPerson((int)Math.round(baseSupply.getGreatPerson() * modifier))
            .build();
    }

    @Override
    public Supply calcIncomeSupply(Civilization civilization) {
        return getBaseSupply(civilization);
    }

    @Override
    public Supply calcOutcomeSupply(Civilization civilization) {
        return Supply.EMPTY;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
            "name=" + ((getView() == null) ? "null" : getView().getLocalizedName()) +
            ", location=" + tile.getLocation() +
        '}';
    }
}
