package com.tsoft.civilization.civilization.building.granary;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.building.catalog.AbstractBuildingSkill;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.resource.ResourceType;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class GranaryIncomeSkill implements AbstractBuildingSkill {

    public static final GranaryIncomeSkill GRANARY_INCOME_SKILL = new GranaryIncomeSkill();

    @Getter
    private final L10n localizedName = new L10n()
        .put(EN, "+1 Food Each source of wheat, bananas and deer worked by the city produces")
        .put(RU, "+1 Food На каждый источник пшеницы, бананов и оленей, обрабатываемых городом");

    public Supply calcSupply(AbstractBuilding building) {
        City city = building.getCity();
        if (city == null) {
            return Supply.EMPTY;
        }

        int food = 0;
        for (AbstractTerrain tile : city.getCitizenTiles()) {
            if (tile.hasResources(ResourceType.WHEAT, ResourceType.BANANAS, ResourceType.DEER)) {
                food ++;
            }
        }

        if (food == 0) {
            return Supply.EMPTY;
        }

        return Supply.builder().food(food).build();
    }
}
