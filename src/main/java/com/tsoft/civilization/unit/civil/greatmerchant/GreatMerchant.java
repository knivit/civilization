package com.tsoft.civilization.unit.civil.greatmerchant;

import com.tsoft.civilization.combat.service.CombatStrength;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;
import lombok.Getter;

import java.util.UUID;

/**
 * Movement: 2; Strength: 0; Ranged Strength: 0
 *
 * Notes: Can construct a Customs House improvement, conduct a Trade Mission (to a city-state),
 * or trigger a Golden Age. The Trade Mission can be activated in the territory of any city-state
 * (with which you are at peace), and by consuming the Great Merchant, will produce gold and increased
 * influence with that city-state. Trade Missions can no longer be conducted with other civilizations.
 */
public class GreatMerchant extends AbstractUnit {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final CombatStrength COMBAT_STRENGTH = CombatStrength.builder()
        .defenseStrength(0)
        .build();

    @Getter
    private final int baseProductionCost = 1;

    private static final GreatMerchantView VIEW = new GreatMerchantView();

    public GreatMerchant(Civilization civilization) {
        super(civilization);
    }

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.CIVIL;
    }

    @Override
    public CombatStrength getBaseCombatStrength() {
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
    public int getProductionCost(Civilization civilization) {
        return -1;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }

    @Override
    public int getGoldCost(Civilization civilization) {
        return -1;
    }
}
