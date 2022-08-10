package com.tsoft.civilization.tile.improvement;

import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatStrength;
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

    @Getter
    private CombatDamage combatDamage = CombatDamage.builder()
        .damage(0)
        .build();

    public abstract boolean acceptEraAndTechnology(Civilization civilization);
    public abstract boolean acceptTile(AbstractTerrain tile);
    public abstract Supply getSupply();
    public abstract CombatStrength getBaseCombatStrength(int era);
    public abstract AbstractImprovementView getView();

    protected AbstractImprovement(AbstractTerrain tile) {
        this.tile = tile;
    }

    protected void init(City city) {
        tile.setImprovement(this);
        this.city = city;
    }

    public void addCombatDamage(CombatDamage damage) {
        combatDamage = combatDamage.add(damage);
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
