package com.tsoft.civilization.tile.feature.coast;

import com.tsoft.civilization.unit.action.move.PassCost;
import com.tsoft.civilization.unit.action.move.PassCostList;
import com.tsoft.civilization.unit.catalog.greatartist.GreatArtist;
import com.tsoft.civilization.unit.catalog.greatengineer.GreatEngineer;
import com.tsoft.civilization.unit.catalog.greatgeneral.GreatGeneral;
import com.tsoft.civilization.unit.catalog.greatmerchant.GreatMerchant;
import com.tsoft.civilization.unit.catalog.greatscientist.GreatScientist;
import com.tsoft.civilization.unit.catalog.settlers.Settlers;
import com.tsoft.civilization.unit.catalog.workers.Workers;
import com.tsoft.civilization.unit.catalog.archers.Archers;
import com.tsoft.civilization.unit.catalog.warriors.Warriors;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.technology.Technology.NAVIGATION;
import static com.tsoft.civilization.unit.action.move.PassCost.UNPASSABLE;

public final class CoastPassCostTable {

    private CoastPassCostTable() { }

    public static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Archers.CLASS_UUID, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 1)));
        table.put(GreatArtist.CLASS_UUID, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 1)));
        table.put(GreatEngineer.CLASS_UUID, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 1)));
        table.put(GreatGeneral.CLASS_UUID, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 1)));
        table.put(GreatMerchant.CLASS_UUID, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 1)));
        table.put(GreatScientist.CLASS_UUID, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 1)));
        table.put(Settlers.CLASS_UUID, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 1)));
        table.put(Warriors.CLASS_UUID, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 1)));
        table.put(Workers.CLASS_UUID, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 1)));
    }
}
