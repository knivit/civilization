package com.tsoft.civilization.unit.service.move;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.tile.feature.FeatureList;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.AbstractDir;
import com.tsoft.civilization.util.Dir6;
import com.tsoft.civilization.util.Point;

import java.util.*;

public class MoveUnitService {

    public static final ActionSuccessResult UNIT_MOVED = new ActionSuccessResult(L10nUnit.UNIT_MOVED);
    public static final ActionSuccessResult UNIT_SWAPPED = new ActionSuccessResult(L10nUnit.UNIT_SWAPPED);
    public static final ActionSuccessResult CAN_MOVE = new ActionSuccessResult(L10nUnit.CAN_MOVE);

    public static final ActionFailureResult NO_PASS_SCORE = new ActionFailureResult(L10nUnit.NO_PASS_SCORE);
    public static final ActionFailureResult CANT_CROSS_BORDERS = new ActionFailureResult(L10nUnit.CANT_CROSS_BORDERS);
    public static final ActionFailureResult UNIT_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult UNIT_DESTROYED = new ActionFailureResult(L10nUnit.UNIT_DESTROYED);
    public static final ActionFailureResult INVALID_UNIT_LOCATION = new ActionFailureResult(L10nUnit.INVALID_UNIT_LOCATION);
    public static final ActionFailureResult INVALID_TARGET_LOCATION = new ActionFailureResult(L10nUnit.INVALID_TARGET_LOCATION);
    public static final ActionFailureResult NO_LOCATIONS_TO_MOVE = new ActionFailureResult(L10nUnit.NO_LOCATIONS_TO_MOVE);

    // Move the unit along the given route
    // All passed steps are removed from the route
    public ArrayList<ActionAbstractResult> moveByRoute(AbstractUnit unit, UnitRoute route) {
        route.saveOriginalSize();

        boolean isMoved = false;
        ArrayList<ActionAbstractResult> moveResults = new ArrayList<>();
        while (!route.isEmpty()) {
            // get the next location
            AbstractDir dir = route.getDirs().get(0);
            Point nextLocation = unit.getTilesMap().addDirToLocation(unit.getLocation(), dir);

            // check we can move to it
            ActionAbstractResult moveResult = getMoveResult(unit, nextLocation, (route.getOriginalSize() == 1));
            moveResults.add(moveResult);
            if (moveResult.isFail()) {
                break;
            }

            // go there
            moveUnit(unit, nextLocation);
            route.getDirs().remove(0);
            isMoved = true;
        }

        // If the unit has moved, then send the notifications
        if (isMoved) {
            unit.getWorld().sendEvent(UnitMovedEvent.builder()
                .unitName(unit.getView().getName())
                .build());
        }

        return moveResults;
    }

    public ActionAbstractResult move(AbstractUnit unit, Point location) {
        if (location == null) {
            return INVALID_TARGET_LOCATION;
        }

        ActionAbstractResult result = canMove(unit);
        if (result.isFail()) {
            return result;
        }

        UnitRoute route = findRoute(unit, location);
        ArrayList<ActionAbstractResult> results = moveByRoute(unit, route);
        for (ActionAbstractResult moveResult : results) {
            if (moveResult.isFail()) {
                return INVALID_TARGET_LOCATION;
            }
        }

        return UNIT_MOVED;
    }

    public ActionAbstractResult canMove(AbstractUnit unit) {
        if (unit == null) {
            return UNIT_NOT_FOUND;
        }

        if (unit.isDestroyed()) {
            return UNIT_DESTROYED;
        }

        if (unit.getLocation() == null) {
            return INVALID_UNIT_LOCATION;
        }

        Set<Point> locations = getLocationsToMove(unit);
        if (locations.size() == 0) {
            return NO_LOCATIONS_TO_MOVE;
        }

        return CAN_MOVE;
    }

    public void moveUnit(AbstractUnit unit, Point location) {
        if (!swap(unit, location)) {
            if (unit.getCivilization().getUnitService().canBePlaced(unit, location)) {
                doMoveUnit(unit, location);
            }
        }
    }

    // Try to move one unit on another - they must be swapped
    // conditions:
    // 1) both are the same civilization
    // 2) both units have movements
    // 3) they are located near each other
    // 4) they are the same unit type
    private boolean swap(AbstractUnit unit, Point location) {
        if (unit.getTilesMap().isTilesNearby(unit.getLocation(), location)) {
            UnitList other = unit.getCivilization().getUnitService().getUnitsAtLocation(location);
            AbstractUnit swapUnit = other.findUnitByCategory(unit.getUnitCategory());

            if (swapUnit != null && unit.getCivilization().equals(swapUnit.getCivilization()) && swapUnit.isActionAvailable()) {
                doMoveUnit(swapUnit, unit.getLocation());
                doMoveUnit(unit, location);
                return true;
            }
        }

        return false;
    }

    // All the checks was made - just do the move
    private void doMoveUnit(AbstractUnit unit, Point location) {
        unit.getMovementService().setLocation(location);

        AbstractTile tile = unit.getCivilization().getTilesMap().getTile(location);
        int tilePassCost = getPassCost(unit.getCivilization(), unit, tile);
        unit.setPassScore(unit.getPassScore() - tilePassCost);
    }

    public ActionAbstractResult canMoveOnTile(AbstractUnit unit, Point location) {
        AbstractTile tile = unit.getTilesMap().getTile(location);
        int tilePassCost = getPassCost(unit.getCivilization(), unit, tile);

        int passScore = unit.getPassScore();
        if (passScore < tilePassCost) {
            return NO_PASS_SCORE;
        }

        Civilization civilization = unit.getWorld().getCivilizationOnTile(location);

        // The tile is nobody's or mine
        if (civilization == null || unit.getCivilization().equals(civilization)) {
            return CAN_MOVE;
        }

        // The tile belongs to other civilization and we can't cross borders
        if (!civilization.canCrossBorders(unit.getCivilization())) {
            return CANT_CROSS_BORDERS;
        }

        // The relations allow to move on the foreign tile
        return CAN_MOVE;
    }

    private static ActionAbstractResult checkForeignUnits(AbstractUnit unit, Point location) {
        UnitList units = unit.getWorld().getUnitsAtLocation(location, unit.getCivilization());
        if (units.isEmpty()) {
            return CAN_MOVE;
        }

        return INVALID_TARGET_LOCATION;
    }

    public UnitRoute findRoute(AbstractUnit unit, Point targetLocation) {
        UnitRoute unitRoute = new UnitRoute();
        if ((targetLocation == null) || (unit.getLocation().equals(targetLocation))) {
            return unitRoute;
        }

        // get the route from finish to start
        // Mountains have Integer.MAX_VALUE passing cost,
        // so here we use Integer.MAX_VALUE - 1
        LinkedList<AbstractDir> dirs = new LinkedList<>();
        Map<Point, DirPassScore> tileScores = getAllLocationsDirPassScore(unit, Integer.MAX_VALUE - 1, targetLocation);
        while (!targetLocation.equals(unit.getLocation())) {
            DirPassScore tileScore = tileScores.get(targetLocation);

            // there is no route at all
            if (tileScore == null) {
                return unitRoute;
            }
            AbstractDir dir = tileScore.getDir();
            dirs.addFirst(dir);

            // go back
            int tileY = tileScore.getTileY();
            AbstractDir inverseDir = dir.getInverse(tileY);
            targetLocation = unit.getTilesMap().addDirToLocation(targetLocation, inverseDir);
        }

        unitRoute.addAll(dirs);
        return unitRoute;
    }

    private Map<Point, DirPassScore> getAllLocationsDirPassScore(AbstractUnit unit, int passScore, Point finishLocation) {
        TilesMap tilesMap = unit.getTilesMap();

        Map<Point, DirPassScore> tileScores = new HashMap<>();
        tileScores.put(unit.getLocation(), new DirPassScore(passScore, null, 0));

        ArrayList<Point> currLocations = new ArrayList<>(tileScores.keySet());

        boolean foundFinish = false;
        while (currLocations.size() > 0 && !foundFinish) {
            ArrayList<Point> nextLocations = new ArrayList<>();

            for (Point currLocation : currLocations) {
                DirPassScore dirPassScore = tileScores.get(currLocation);
                passScore = dirPassScore.getPassScore();
                if (passScore <= 0) {
                    continue;
                }

                // look around
                for (Dir6 dir : Dir6.staticGetDirs(currLocation.getY())) {
                    Point nextLocation = tilesMap.addDirToLocation(currLocation, dir);
                    AbstractTile nextTile = tilesMap.getTile(nextLocation);

                    int nextPassScore = passScore - getPassCost(unit.getCivilization(), unit, nextTile);
                    if (nextPassScore >= 0) {
                        // check for foreign city located there
                        boolean isBlocked = false;
                        City city = unit.getWorld().getCityAtLocation(nextLocation);
                        if (city != null && !city.getCivilization().equals(unit.getCivilization())) {
                            isBlocked = true;
                        }

                        // check for units
                        UnitList units = unit.getWorld().getUnitsAtLocation(nextLocation);
                        if (!units.isEmpty()) {
                            isBlocked = true;
                        }

                        // for blocks, set max pass score
                        DirPassScore tileScore = tileScores.get(nextLocation);
                        if (tileScore == null || tileScore.getPassScore() < nextPassScore) {
                            int tileY = currLocation.getY();
                            tileScores.put(nextLocation, (isBlocked ? new DirPassScore(0, dir, tileY) : new DirPassScore(nextPassScore, dir, tileY)));
                            if (finishLocation != null && finishLocation.equals(nextLocation)) {
                                foundFinish = true;
                            }
                            nextLocations.add(nextLocation);
                        }
                    }
                }
            }

            currLocations = nextLocations;
        }

        tileScores.remove(unit.getLocation());
        return tileScores;
    }

    // Find out ALL tiles available to move in
    // Any units and foreign cities blocks further movement
    // (i.e. when there is a narrow passage the enter of which
    // is blocked by any unit or foreign city, we can not pass trough it)
    private Set<Point> getCandidatesToMove(AbstractUnit unit) {
        int passScore = unit.getPassScore();
        if (passScore == 0) {
            return Collections.emptySet();
        }

        Map<Point, DirPassScore> tileScores = getAllLocationsDirPassScore(unit, unit.getPassScore(), null);
        return tileScores.keySet();
    }

    // Find out tiles possible to move in
    public Set<Point> getLocationsToMove(AbstractUnit unit) {
        Set<Point> locations = getCandidatesToMove(unit);

        // Exclude tiles occupied by
        // - other the same kind units from this civilization
        // - all foreign units
        // - all foreign cities
        UnitList units = unit.getWorld().getUnitsAtLocations(locations);
        for (AbstractUnit ua : units) {
            if (unit.getCivilization().equals(ua.getCivilization())) {
                if (unit.getUnitCategory().isMilitary() == ua.getUnitCategory().isMilitary()) {
                    locations.remove(ua.getLocation());
                }
            } else {
                locations.remove(ua.getLocation());
            }
        }

        CityList cities = unit.getWorld().getCitiesAtLocations(locations);
        for (City city : cities) {
            if (!city.getCivilization().equals(unit.getCivilization())) {
                locations.remove(city.getLocation());
            }
        }

        return locations;
    }

    // Check can we move there
    public ActionAbstractResult getMoveResult(AbstractUnit unit, Point nextLocation, boolean canSwapLocations) {
        ActionAbstractResult moveResult;

        // check is the passing score enough
        moveResult = canMoveOnTile(unit, nextLocation);
        if (moveResult.isFail()) {
            return moveResult;
        }

        // check entering into a city
        City city = unit.getWorld().getCityAtLocation(nextLocation);
        if (city != null) {
            return canEnterCity(unit, city);
        }

        // check are there foreign units
        moveResult = checkForeignUnits(unit, nextLocation);
        if (moveResult.isFail()) {
            return moveResult;
        }

        // check is it a units swapping
        moveResult = checkUnitsSwap(unit, nextLocation, canSwapLocations);
        if (moveResult.isFail()) {
            return moveResult;
        }
        return UNIT_MOVED;
    }

    private ActionAbstractResult canEnterCity(AbstractUnit unit, City city) {
        if (unit.getCivilization().equals(city.getCivilization())) {
            return canEnterOwnCity(unit, city);
        }

        return canEnterForeignCity(unit, city);
    }

    private ActionAbstractResult canEnterOwnCity(AbstractUnit unit, City city) {
        // get units located in the city
        UnitList units = unit.getCivilization().getUnitService().getUnitsAtLocation(city.getLocation());
        AbstractUnit cityUnit = units.findUnitByCategory(unit.getUnitCategory());

        // no units of such type, so we can enter into city
        if (cityUnit == null) {
            return CAN_MOVE;
        }

        return INVALID_TARGET_LOCATION;
    }

    private ActionAbstractResult canEnterForeignCity(AbstractUnit unit, City city) {
        return INVALID_TARGET_LOCATION;
    }

    private ActionAbstractResult checkUnitsSwap(AbstractUnit unit, Point nextLocation, boolean canSwapLocations) {
        UnitList units = unit.getCivilization().getUnitService().getUnitsAtLocation(nextLocation);
        AbstractUnit nextUnit = units.findUnitByCategory(unit.getUnitCategory());
        if (nextUnit == null) {
            return INVALID_TARGET_LOCATION;
        }

        // swapping must be the only dir on the route
        if (!canSwapLocations) {
            return INVALID_TARGET_LOCATION;
        }

        // next unit must have enough passing score
        Point thisLocation = unit.getLocation();
        ActionAbstractResult moveResult = canMoveOnTile(nextUnit, thisLocation);
        if (moveResult.isFail()) {
            return moveResult;
        }

        // looks good, let's swap them
        moveUnit(nextUnit, thisLocation);
        return UNIT_SWAPPED;
    }

    public int getPassCost(Civilization civilization, AbstractUnit unit, AbstractTile tile) {
        int passCost = TilePassCostTable.get(civilization, unit, tile);

        FeatureList features = tile.getFeatures();
        if (features.isEmpty()) {
            return passCost;
        }

        // add features starting from the uppermost
        for (int i = features.size() - 1; i >= 0; i --) {
            AbstractFeature feature = features.get(i);

            int featurePassCost = getFeaturePassCost(unit, feature);
            if (featurePassCost == TilePassCostTable.UNPASSABLE) {
                return TilePassCostTable.UNPASSABLE;
            }

            passCost += featurePassCost;
        }

        return passCost;
    }

    private int getFeaturePassCost(AbstractUnit unit, AbstractFeature feature) {
        return FeaturePassCostTable.get(unit.getCivilization(), unit, feature);
    }
}
