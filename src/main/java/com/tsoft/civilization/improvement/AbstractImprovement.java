package com.tsoft.civilization.improvement;

import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.tile.AbstractTile;
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
    private final AbstractTile tile;

    @Getter
    private CombatDamage combatDamage = CombatDamage.builder()
        .damage(0)
        .build();

    public abstract boolean acceptEraAndTechnology(Civilization civilization);
    public abstract boolean acceptTile(AbstractTile tile);
    public abstract Supply getSupply();
    public abstract CombatStrength getBaseCombatStrength(int era);
    public abstract AbstractImprovementView getView();

    protected AbstractImprovement(AbstractTile tile) {
        this.tile = tile;
    }

    protected void init() {
        tile.setImprovement(this);
    }

    TODO find out which (city) this improvement belongs

    public void addCombatDamage(CombatDamage damage) {
        combatDamage = combatDamage.add(damage);
    }

    public boolean isBlockingTileSupply() {
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
            ", name=" + ((getView() == null) ? "null" : getView().getLocalizedName()) +
            ", location=" + tile.getLocation() +
        '}';
    }
}
