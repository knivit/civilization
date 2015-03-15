package com.tsoft.civilization.unit.util;

import com.tsoft.civilization.unit.Archers;
import com.tsoft.civilization.unit.GreatArtist;
import com.tsoft.civilization.unit.GreatEngineer;
import com.tsoft.civilization.unit.GreatGeneral;
import com.tsoft.civilization.unit.GreatMerchant;
import com.tsoft.civilization.unit.GreatScientist;
import com.tsoft.civilization.unit.Settlers;
import com.tsoft.civilization.unit.Warriors;
import com.tsoft.civilization.unit.Workers;

/** Read-only objects, this list is to use as a catalog only */
public class UnitCatalog {
    private static final UnitCollection unitCatalog = new UnitList();
    private static final UnmodifiableUnitList unmodifiableUnitsCatalog = new UnmodifiableUnitList(unitCatalog);

    static {
        unitCatalog.add(Archers.INSTANCE);
        unitCatalog.add(GreatArtist.INSTANCE);
        unitCatalog.add(GreatEngineer.INSTANCE);
        unitCatalog.add(GreatGeneral.INSTANCE);
        unitCatalog.add(GreatMerchant.INSTANCE);
        unitCatalog.add(GreatScientist.INSTANCE);
        unitCatalog.add(Settlers.INSTANCE);
        unitCatalog.add(Workers.INSTANCE);
        unitCatalog.add(Warriors.INSTANCE);
    }

    public static UnitCollection values() {
        return unmodifiableUnitsCatalog;
    }
}
