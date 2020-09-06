package com.tsoft.civilization.unit;

import com.tsoft.civilization.unit.civil.*;
import com.tsoft.civilization.unit.military.Archers;
import com.tsoft.civilization.unit.military.Warriors;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.unit.util.UnitList;
import com.tsoft.civilization.civilization.Civilization;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
public final class UnitFactory {

    private UnitFactory() { }

    public static <T extends AbstractUnit<?>> T newInstance(String classUuid) {
        T unit = (T)createUnit(classUuid);
        unit.init();

        return unit;
    }

    private static final Map<String, Supplier<AbstractUnit<?>>> UNIT_CATALOG = new HashMap<>();

    static {
        UNIT_CATALOG.put(Archers.CLASS_UUID, Archers::new);

        UNIT_CATALOG.put(GreatArtist.CLASS_UUID, GreatArtist::new);
        UNIT_CATALOG.put(GreatEngineer.CLASS_UUID, GreatEngineer::new);
        UNIT_CATALOG.put(GreatGeneral.CLASS_UUID, GreatGeneral::new);
        UNIT_CATALOG.put(GreatMerchant.CLASS_UUID, GreatMerchant::new);
        UNIT_CATALOG.put(GreatScientist.CLASS_UUID, GreatScientist::new);

        UNIT_CATALOG.put(Settlers.CLASS_UUID, Settlers::new);
        UNIT_CATALOG.put(Warriors.CLASS_UUID, Warriors::new);
        UNIT_CATALOG.put(Workers.CLASS_UUID, Workers::new);
    }

    public static UnitCollection getPossibleUnits(Civilization civilization) {
        UnitCollection result = new UnitList();

        for (Supplier<AbstractUnit<?>> supplier : UNIT_CATALOG.values()) {
            AbstractUnit<?> unit = supplier.get();
            if (unit.checkEraAndTechnology(civilization)) {
                result.add(unit);
            }
        }

        return result;
    }

    public static AbstractUnit<?> createUnit(String unitClassUuid) {
        Supplier<AbstractUnit<?>> supplier = UNIT_CATALOG.get(unitClassUuid);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown unit classUuid = " + unitClassUuid);
        }
        return supplier.get();
    }
}
