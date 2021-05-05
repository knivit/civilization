package com.tsoft.civilization.tile.feature.hill;

import com.tsoft.civilization.unit.move.PassCost;
import com.tsoft.civilization.unit.move.PassCostList;
import com.tsoft.civilization.unit.civil.greatartist.GreatArtist;
import com.tsoft.civilization.unit.civil.greatengineer.GreatEngineer;
import com.tsoft.civilization.unit.civil.greatgeneral.GreatGeneral;
import com.tsoft.civilization.unit.civil.greatmerchant.GreatMerchant;
import com.tsoft.civilization.unit.civil.greatscientist.GreatScientist;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;

import java.util.HashMap;
import java.util.Map;

public final class HillPassCostTable {

    private HillPassCostTable() { }


    public static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Archers.CLASS_UUID, PassCostList.of(new PassCost(null, 1)));
        table.put(GreatArtist.CLASS_UUID, PassCostList.of(new PassCost(null, 1)));
        table.put(GreatEngineer.CLASS_UUID, PassCostList.of(new PassCost(null, 1)));
        table.put(GreatGeneral.CLASS_UUID, PassCostList.of(new PassCost(null, 1)));
        table.put(GreatMerchant.CLASS_UUID, PassCostList.of(new PassCost(null, 1)));
        table.put(GreatScientist.CLASS_UUID, PassCostList.of(new PassCost(null, 1)));
        table.put(Settlers.CLASS_UUID, PassCostList.of(new PassCost(null, 1)));
        table.put(Warriors.CLASS_UUID, PassCostList.of(new PassCost(null, 1)));
        table.put(Workers.CLASS_UUID, PassCostList.of(new PassCost(null, 1)));
    }
}
