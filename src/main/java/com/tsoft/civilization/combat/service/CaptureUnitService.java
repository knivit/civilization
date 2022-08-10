package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.civilization.event.CityCapturedEvent;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.L10nCombat;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.service.move.MoveUnitService;
import com.tsoft.civilization.util.Point;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CaptureUnitService {

    public static final ActionSuccessResult CAN_CAPTURE = new ActionSuccessResult(L10nCombat.CAN_CAPTURE);
    public static final ActionSuccessResult UNIT_CAPTURED = new ActionSuccessResult(L10nCombat.UNIT_CAPTURED);

    public static final ActionFailureResult NO_PASS_SCORE = new ActionFailureResult(L10nUnit.NO_PASS_SCORE);
    public static final ActionFailureResult CANT_CAPTURE_CITY = new ActionFailureResult(L10nUnit.CANT_CAPTURE_CITY);
    public static final ActionFailureResult NO_LOCATIONS_TO_CAPTURE = new ActionFailureResult(L10nCombat.NO_LOCATIONS_TO_CAPTURE);
    public static final ActionFailureResult NOTHING_TO_CAPTURE = new ActionFailureResult(L10nCombat.NOTHING_TO_CAPTURE);
    public static final ActionFailureResult ATTACKER_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult INVALID_TARGET_LOCATION = new ActionFailureResult(L10nCombat.INVALID_TARGET_LOCATION);

    private static final BaseCombatService baseCombatService = new BaseCombatService();
    private final MoveUnitService moveUnitService = new MoveUnitService();

    public AbstractUnit getTargetToCaptureAtLocation(AbstractUnit capturer, Point location) {
        UnitList foreignUnits = capturer.getWorld().getUnitsAtLocation(location, capturer.getCivilization());
        if (foreignUnits.isEmpty()) {
            return null;
        }

        // if there are military units, protecting the civilians, then there is nothing to capture
        if (foreignUnits.getMilitaryCount() > 0) {
            return null;
        }

        AbstractUnit foreignUnit = foreignUnits.getAny();
        if (foreignUnit.canBeCaptured()) {
            return foreignUnit;
        }
        return null;
    }

    public List<Point> getLocationsToCapture(AbstractUnit unit) {
        UnitList units = getTargetsToCapture(unit);
        return units.getLocations();
    }

    public ActionAbstractResult capture(AbstractUnit attacker, Point location) {
        if (attacker == null || attacker.isDestroyed()) {
            return ATTACKER_NOT_FOUND;
        }

        if (location == null) {
            return INVALID_TARGET_LOCATION;
        }

        Collection<Point> locations = getLocationsToCapture(attacker);
        if (!locations.contains(location)) {
            return NOTHING_TO_CAPTURE;
        }

        AbstractUnit victim = getTargetToCaptureAtLocation(attacker, location);
        if (victim == null) {
            return NO_LOCATIONS_TO_CAPTURE;
        }

        // move unit
        moveUnitService.doMoveUnit(attacker, victim.getLocation());

        // capture foreign unit
        capture(attacker, victim);

        // passScore ends
        attacker.setPassScore(0);
        return UNIT_CAPTURED;
    }

    public ActionAbstractResult canCapture(AbstractUnit attacker) {
        if (attacker == null || attacker.isDestroyed()) {
            return ATTACKER_NOT_FOUND;
        }

        Collection<Point> locations = getLocationsToCapture(attacker);
        if (locations.isEmpty()) {
            return NO_LOCATIONS_TO_CAPTURE;
        }

        return CAN_CAPTURE;
    }

    // Do not destroy unit; remove it from it's civilization and reassign to the winner's
    private void capture(HasCombatStrength attacker, AbstractUnit victim) {
        // a war started
        attacker.getCivilization().getWorld().setCivilizationsRelations(attacker.getCivilization(), victim.getCivilization(),
            CivilizationsRelations.war());

        // remove the victim from it's civilization
        victim.getCivilization().getUnitService().removeUnit(victim);

        // re-create unit id so it can't be used anymore
        victim.setId(UUID.randomUUID().toString());

        // captured unit can't move
        victim.setPassScore(0);

        // reassign to the winner
        Civilization winner = attacker.getCivilization();
        victim.setCivilization(winner);
        winner.getUnitService().addUnit(victim, victim.getLocation());
    }

    public void captureCity(HasCombatStrength attacker, City city) {
        Civilization winner = attacker.getCivilization();

        city.destroy();
        city.setPassScore(0);

        winner.getWorld().sendEvent(CityCapturedEvent.builder()
            .winner(winner.getView().getName())
            .cityName(city.getName())
            .build());

        // remove the city from its civilization
        city.getCivilization().getCityService().removeCity(city);

        // destroy all military units located in the city and capture civilians
        UnitList victims = winner.getWorld().getUnitsAtLocation(city.getLocation());
        for (AbstractUnit victim : victims) {
            if (victim.getUnitCategory().isMilitary()) {
                baseCombatService.destroy(attacker, victim);
            } else {
                capture(attacker, victim);
            }
        }

        // pass the city to the winner's civilization
        city.moveToCivilization(winner);
        winner.getCityService().addCity(city);
    }

    private UnitList getTargetsToCapture(AbstractUnit capturer) {
        UnitList units = new UnitList();

        Collection<Point> locations = capturer.getTilesMap().getLocationsAround(capturer.getLocation(), 1);
        for (Point location : locations) {
            // check we can move to the location
            ActionAbstractResult moveResult = getMoveOnCaptureResult(capturer, location);
            if (moveResult.isFail()) {
                continue;
            }

            AbstractUnit foreignUnit = getTargetToCaptureAtLocation(capturer, location);
            if (foreignUnit != null) {
                units.add(foreignUnit);
            }
        }

        return units;
    }

    // Check can we move there during a capturing
    private ActionAbstractResult getMoveOnCaptureResult(AbstractUnit unit, Point dest) {
        // check is the passing score enough
        AbstractTerrain tile = unit.getTilesMap().getTile(dest);
        int tilePassCost = moveUnitService.getPassCost(unit.getCivilization(), unit, tile);
        int passScore = unit.getPassScore();
        if (passScore < tilePassCost) {
            return NO_PASS_SCORE;
        }

        // we can't capture a city
        City city = unit.getWorld().getCityAtLocation(dest);
        if (city != null) {
            return CANT_CAPTURE_CITY;
        }

        return CAN_CAPTURE;
    }
}
