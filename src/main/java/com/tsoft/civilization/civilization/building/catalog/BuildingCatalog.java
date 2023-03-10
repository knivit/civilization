package com.tsoft.civilization.civilization.building.catalog;

import com.tsoft.civilization.civilization.building.BuildingBaseState;
import com.tsoft.civilization.civilization.building.BuildingType;
import com.tsoft.civilization.civilization.building.catalog.granary.GranaryBaseState;
import com.tsoft.civilization.civilization.building.catalog.market.MarketBaseState;
import com.tsoft.civilization.civilization.building.catalog.monument.MonumentBaseState;
import com.tsoft.civilization.civilization.building.catalog.palace.PalaceBaseState;
import com.tsoft.civilization.civilization.building.catalog.settlement.SettlementBaseState;
import com.tsoft.civilization.civilization.building.catalog.walls.WallsBaseState;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.civilization.building.BuildingType.*;

public final class BuildingCatalog {

    private static final Map<BuildingType, BuildingBaseState> BUILDINGS = new HashMap<>() {{
        put(GRANARY, new GranaryBaseState().getBaseState());
        put(MARKET, new MarketBaseState().getBaseState());
        put(MONUMENT, new MonumentBaseState().getBaseState());
        put(PALACE, new PalaceBaseState().getBaseState());
        put(SETTLEMENT, new SettlementBaseState().getBaseState());
        put(WALLS, new WallsBaseState().getBaseState());
    }};

    private BuildingCatalog() { }

    public static BuildingBaseState getBaseState(BuildingType type) {
        return BUILDINGS.get(type);
    }
}
