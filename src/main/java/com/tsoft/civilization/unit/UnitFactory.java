package com.tsoft.civilization.unit;

import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.greatartist.GreatArtist;
import com.tsoft.civilization.unit.civil.greatengineer.GreatEngineer;
import com.tsoft.civilization.unit.civil.greatgeneral.GreatGeneral;
import com.tsoft.civilization.unit.civil.greatmerchant.GreatMerchant;
import com.tsoft.civilization.unit.civil.greatscientist.GreatScientist;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.civilization.Civilization;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class UnitFactory {

    private static final Map<String, AbstractUnit> CATALOG = new HashMap<>();
    private static final Map<String, Function<Civilization, AbstractUnit>> FACTORY = new HashMap<>();

    static {
        FACTORY.put(Archers.CLASS_UUID, Archers::new);

        FACTORY.put(GreatArtist.CLASS_UUID, GreatArtist::new);
        FACTORY.put(GreatEngineer.CLASS_UUID, GreatEngineer::new);
        FACTORY.put(GreatGeneral.CLASS_UUID, GreatGeneral::new);
        FACTORY.put(GreatMerchant.CLASS_UUID, GreatMerchant::new);
        FACTORY.put(GreatScientist.CLASS_UUID, GreatScientist::new);

        FACTORY.put(Settlers.CLASS_UUID, Settlers::new);
        FACTORY.put(Warriors.CLASS_UUID, Warriors::new);
        FACTORY.put(Workers.CLASS_UUID, Workers::new);

        FACTORY.forEach((k, v) -> CATALOG.put(k, v.apply(null)));
    }

    private UnitFactory() { }

    public static <T extends AbstractUnit> T newInstance(Civilization civilization, String classUuid) {
        Function<Civilization, AbstractUnit> creator = FACTORY.get(classUuid);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown unit classUuid = " + classUuid);
        }

        T unit = (T)creator.apply(civilization);
        unit.init();
        return unit;
    }

    public static <T extends AbstractUnit> T findByClassUuid(String classUuid) {
        return (T)CATALOG.get(classUuid);
    }

    public static UnitList getAvailableUnits(Civilization civilization) {
        UnitList result = new UnitList();

        for (AbstractUnit unit : CATALOG.values()) {
            if (unit.checkEraAndTechnology(civilization)) {
                result.add(unit);
            }
        }

        return result;
    }
}
