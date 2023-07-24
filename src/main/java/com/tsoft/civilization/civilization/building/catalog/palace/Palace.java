package com.tsoft.civilization.civilization.building.catalog.palace;

import com.tsoft.civilization.civilization.building.*;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.Year;

/**
 * Palace
 * ------
 * National wonder in Civilization V
 * Production cost: 1 Production
 * Culture: 1 Culture
 * Effect
 *   +3 Production
 *   +3 Science
 *   +3 Gold
 *   +2 Melee strength Defense
 * Indicates the capital city
 * Cities connected by a road to the capital produce additional gold
 *
 * Game Info
 *
 * Indicates that a given city is the Capital of the empire. Built automatically in the first city you establish.
 *
 * Connecting other cities to the Capital by Road will produce additional Gold
 * +3 Production, +3 Science, +3 Gold, +1 Culture
 * +2 Melee strength Defense
 * BNW-only.png 1 Great Work of Art or Artifact slot
 * +1 Production, Culture, Gold, Faith and Science with God King Religious Belief
 *
 * Strategy
 *
 * Your Palace is the seat of your government. In the very beginning of the game it provides a significant portion
 * of your capital city's Gold and Production, boosts Science output, and is the only
 * source of Culture. It also forms the heart of your empire's trading network.
 *
 * At all times, the Palace is considered the seat of your government in regards to domination victory.
 * Although the Palace is automatically rebuilt in another city if your capital is captured, it can still be a
 * terrible blow to your civilization (and also makes you ineligible to win a domination victory until you get it back).
 * Protect your Palace well!
 *
 * Civilopedia entry
 *
 * A palace is the residence of a civilization's ruler. The term is somewhat anachronistic, dating back to a time when
 * most countries were ruled by kings or other hereditary leaders. Nowadays modern rulers live in ornate buildings
 * called something like "the People's House" or "the Place of Justice," but the effect is the same.
 * Palaces (and their modern equivalents) are designed to do three things: to provide the ruler with access to
 * the people and communications necessary to rule, to defend the leader from attack, and to impress upon subjects
 * and foreign visitors the leader's importance and grandeur.
 */
public class Palace extends AbstractBuilding {

    public static final String CLASS_UUID = BuildingType.PALACE.name();

    private static final BuildingBaseState BASE_STATE = new PalaceBaseState().getBaseState();

    private static final AbstractBuildingView VIEW = new PalaceView();

    public Palace(City city) {
        super(city);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public BuildingBaseState getBaseState() {
        return BASE_STATE;
    }

    @Override
    public AbstractBuildingView getView() {
        return VIEW;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }

    @Override
    public boolean requiredEraAndTechnology(Civilization civilization) {
        return true;
    }
}
