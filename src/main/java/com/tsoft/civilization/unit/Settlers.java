package com.tsoft.civilization.unit;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.web.view.unit.SettlersView;
import com.tsoft.civilization.world.Civilization;

import java.util.UUID;

/**
 * Movement: 2;
 * Strength: 0;
 * Ranged Strength: 0;
 * Cost: 106 hammers;
 * Requires Resource: none
 * Technology: (none)
 *
 * Abilities: Found City (B): This order causes the Settler to found a city at its current location.
 * The Settler is consumed in this process.
 * Notes: Growth of the City is stopped while this Units is being built. Settlers may only be built
 * in Cities with at least 2 Citizens. This is the only unit in the game with regional variation:
 * in addition to regional costume, the European version has a mule, the Native American version has a Llama,
 * the Asian version has a water buffalo, and the African/Middle Eastern version has a camel.
 */
public class Settlers extends AbstractUnit<SettlersView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();
    public static final Settlers INSTANCE = new Settlers();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
            .setStrength(0);

    private static final SettlersView VIEW = new SettlersView();

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.CIVIL;
    }

    @Override
    protected CombatStrength getBaseCombatStrength() {
        return COMBAT_STRENGTH;
    }

    @Override
    public void initPassScore() {
        setPassScore(5);
    }

    @Override
    public SettlersView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public int getProductionCost() {
        return 25;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() < Year.MODERN_ERA;
    }

    @Override
    public int getGoldCost() {
        return 200;
    }
}
