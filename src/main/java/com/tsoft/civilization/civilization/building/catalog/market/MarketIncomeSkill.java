package com.tsoft.civilization.civilization.building.catalog.market;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.building.AbstractBuildingSkill;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class MarketIncomeSkill implements AbstractBuildingSkill {

    @Getter
    private final L10n localizedName = new L10n()
        .put(EN, "+25% Gold Each tile worked by the city produces")
        .put(RU, "+25% Gold На каждую клетку, обрабатываемую городом");

    /** +25% Gold */
    @Override
    public Supply calcSupply(AbstractBuilding building) {
        City city = building.getCity();
        if (city == null) {
            return Supply.EMPTY;
        }

        int gold = 0;
        for (AbstractTerrain tile : city.getCitizenTiles()) {
            Supply tileSupply = tile.getTotalSupply(building.getCivilization());
            gold += tileSupply.getGold();
        }

        if (gold == 0) {
            return Supply.EMPTY;
        }

        return Supply.builder().gold((int)Math.round(gold * 0.25)).build();
    }
}
