package com.tsoft.civilization.unit;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.web.view.unit.GreatMerchantView;
import com.tsoft.civilization.world.Civilization;

/**
 * Movement: 2; Strength: 0; Ranged Strength: 0
 *
 * Notes: Can construct a Customs House improvement, conduct a Trade Mission (to a city-state),
 * or trigger a Golden Age. The Trade Mission can be activated in the territory of any city-state
 * (with which you are at peace), and by consuming the Great Merchant, will produce gold and increased
 * influence with that city-state. Trade Missions can no longer be conducted with other civilizations.
 */
public class GreatMerchant extends AbstractUnit<GreatMerchantView> {
    public static final String CLASS_UUID = "77b3aa05-4843-482b-a661-8233136d5fdd";
    public static final GreatMerchant INSTANCE = new GreatMerchant();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
            .setStrength(0);

    private static final GreatMerchantView VIEW = new GreatMerchantView();

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
        setPassScore(2);
    }

    @Override
    public GreatMerchantView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public int getProductionCost() {
        return -1;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }

    @Override
    public int getGoldCost() {
        return -1;
    }
}
