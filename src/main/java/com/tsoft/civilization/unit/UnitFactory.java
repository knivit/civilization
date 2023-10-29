package com.tsoft.civilization.unit;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.SkillList;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class UnitFactory {

    private static final Map<String, AbstractUnit> UNIT_CATALOG = loadCatalog();

    private static final Map<String, UnitBaseState> UNIT_BASE_STATES = new HashMap<>();
    private static final Map<String, AbstractUnitView> UNIT_VIEWS = new HashMap<>();

    private static final Function<JsonUnit, AbstractUnit> SUPPLIER = jsonUnit -> {
        String classUuid = jsonUnit.getName();
        int cost = nvl(jsonUnit.getCost(), 0);

        UnitBaseState baseState = UNIT_BASE_STATES.computeIfAbsent(classUuid,
            e -> UnitBaseState.builder()
                .category(UnitCategory.find(jsonUnit.getUnitType()))
                .goldCost(cost)
                .goldUnitKeepingExpenses(1)
                .productionCost(cost)
                .passCostTable(null)
                .combatStrength(CombatStrength.ZERO)
                .combatSkills(new SkillList<>())
                .healingSkills(new SkillList<>())
                .movementSkills(new SkillList<>())
                .build());

        AbstractUnitView view = UNIT_VIEWS.computeIfAbsent(classUuid,
            e -> new AbstractUnitView(classUuid));

        return new AbstractUnit(classUuid, baseState, view);
    };

    private UnitFactory() { }

    private static Map<String, AbstractUnit> loadCatalog() {
        JsonUnitCatalog.load("/assets/assets/jsons/Civ V - Gods & Kings/Units.json");
        Map<String, AbstractUnit> catalog = new HashMap<>();
        JsonUnitCatalog.entries().forEach(e -> catalog.put(e.getKey(), SUPPLIER.apply(e.getValue())));
        return catalog;
    }

    public static <T extends AbstractUnit> T newInstance(Civilization civilization, String classUuid) {
        JsonUnit jsonUnit = JsonUnitCatalog.get(classUuid);
        AbstractUnit unit = SUPPLIER.apply(jsonUnit);

        unit.init(civilization);
        return (T)unit;
    }

    public static <T extends AbstractUnit> T findByClassUuid(String classUuid) {
        return (T)UNIT_CATALOG.get(classUuid);
    }

    public static UnitList getAvailableUnits(Civilization civilization) {
        UnitList result = new UnitList();

        for (AbstractUnit unit : UNIT_CATALOG.values()) {
            if (unit.checkEraAndTechnology(civilization)) {
                result.add(unit);
            }
        }

        return result;
    }

    private static <T> T nvl(T value, T def) {
        return (value == null) ? def : value;
    }
}
