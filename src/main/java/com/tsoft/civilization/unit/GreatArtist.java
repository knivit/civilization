package com.tsoft.civilization.unit;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.web.view.unit.GreatArtistView;
import com.tsoft.civilization.world.Civilization;

import java.util.UUID;

/**
 * Movement: 2;
 * Strength: 0;
 * Ranged Strength: 0
 *
 * Notes: Can sacrifice itself to construct a Landmark improvement, trigger a Golden Age, or "culture bomb"
 * a tile within 1 space of your territory, putting that tile and all adjacent tiles into your territory.
 * The Culture Bomb target tile cannot be in foreign territory, though it can be in unclaimed territory.
 */
public class GreatArtist extends AbstractUnit<GreatArtistView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();
    public static final GreatArtist INSTANCE = new GreatArtist();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
            .setStrength(20);

    private static final GreatArtistView VIEW = new GreatArtistView();

    @Override
    public UnitType getUnitType() {
        return UnitType.GREAT_ARTIST;
    }

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
    public GreatArtistView getView() {
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
