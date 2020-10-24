package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.UnitRoute;
import com.tsoft.civilization.util.AbstractDir;
import com.tsoft.civilization.util.Dir6;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.event.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MoveUnitAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final TileService tileService = new TileService();

    public static ActionAbstractResult move(AbstractUnit unit, Point location) {
        if (location == null) {
            return MoveUnitActionResults.INVALID_LOCATION;
        }

        ActionAbstractResult result = canMove(unit);
        if (result.isFail()) {
            return result;
        }

        UnitRoute route = findRoute(unit, location);
        ArrayList<UnitMoveResult> results = moveByRoute(unit, route);
        for (UnitMoveResult moveResult : results) {
            if (moveResult.isFailed()) {
                return MoveUnitActionResults.INVALID_LOCATION;
            }
        }

        return MoveUnitActionResults.UNIT_MOVED;
    }

    public static ActionAbstractResult canMove(AbstractUnit unit) {
        if (unit == null || unit.isDestroyed()) {
            return MoveUnitActionResults.UNIT_NOT_FOUND;
        }

        Set<Point> locations = getLocationsToMove(unit);
        if (locations.size() == 0) {
            return MoveUnitActionResults.NO_LOCATIONS_TO_MOVE;
        }

        return MoveUnitActionResults.CAN_MOVE;
    }

    // Move the unit along the given route
    // All passed steps are removed from the route
    public static ArrayList<UnitMoveResult> moveByRoute(AbstractUnit unit, UnitRoute route) {
        route.saveOriginalSize();

        boolean isMoved = false;
        ArrayList<UnitMoveResult> moveResults = new ArrayList<>();
        while (!route.isEmpty()) {
            // get the next location
            AbstractDir dir = route.getDirs().get(0);
            Point nextLocation = unit.getTilesMap().addDirToLocation(unit.getLocation(), dir);

            // check we can move to it
            UnitMoveResult moveResult = getMoveResult(unit, nextLocation, (route.getOriginalSize() == 1));
            moveResults.add(moveResult);
            if (moveResult.isFailed()) {
                break;
            }

            // go there
            unit.moveTo(nextLocation);
            route.getDirs().remove(0);
            isMoved = true;
        }

        // If the unit has moved, then send the notifications
        if (isMoved) {
            unit.getWorld().sendEvent(new Event(Event.UPDATE_WORLD + Event.UPDATE_STATUS_PANEL + Event.UPDATE_CONTROL_PANEL, unit, L10nUnit.UNIT_MOVED_EVENT));
        }

        return moveResults;
    }

    // Check can we move there
    public static UnitMoveResult getMoveResult(AbstractUnit unit, Point nextLocation, boolean canSwapLocations) {
        UnitMoveResult moveResult;

        // check is the passing score enough
        moveResult = checkCanMoveOnTile(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        // check entering into own city
        moveResult = checkOwnCity(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        // check is this tile belongs a civilization which is not in a war with our
        moveResult = checkForeignTile(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        // check is there a foreign city on tile
        moveResult = checkForeignCity(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        // check are there foreign units
        moveResult = checkForeignUnits(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        // check is it a units swapping
        moveResult = checkUnitsSwap(unit, nextLocation, canSwapLocations);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }
        return UnitMoveResult.SUCCESS_MOVED;
    }

    // Check can we move there during melee attack
    public static UnitMoveResult getMoveOnAttackResult(AbstractUnit unit, Point nextLocation) {
        UnitMoveResult moveResult;

        // check is the passing score enough
        moveResult = checkCanMoveOnTile(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        // check entering into own city
        moveResult = checkOwnCity(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        // check is this tile belongs a civilization which is not in a war with our
        moveResult = checkForeignTile(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        return UnitMoveResult.SUCCESS_MOVED;
    }

    // Check can we move there during a capturing
    public static UnitMoveResult getMoveOnCaptureResult(AbstractUnit unit, Point nextLocation) {
        UnitMoveResult moveResult;

        // check is the passing score enough
        moveResult = checkCanMoveOnTile(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        // check entering into own city
        moveResult = checkOwnCity(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        // check is this tile belongs a civilization which is not in a war with our
        moveResult = checkForeignTile(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        // check is there a foreign city on tile
        moveResult = checkForeignCity(unit, nextLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return moveResult;
        }

        return UnitMoveResult.SUCCESS_MOVED;
    }

    private static UnitMoveResult checkCanMoveOnTile(AbstractUnit unit, Point location) {
        AbstractTile tile = unit.getTilesMap().getTile(location);
        int tilePassCost = tileService.getPassCost(unit, tile);

        int passScore = unit.getPassScore();
        if ((passScore - tilePassCost) < 0) {
            return UnitMoveResult.FAIL_NOT_ENOUGH_PASSING_SCORE;
        }
        return UnitMoveResult.CHECK_FAILED;
    }

    private static UnitMoveResult checkOwnCity(AbstractUnit unit, Point location) {
        Civilization thisCivilization = unit.getCivilization();
        City city = thisCivilization.cities().getCityAtLocation(location);
        if (city == null) {
            return UnitMoveResult.CHECK_FAILED;
        }

        // get units located in the city
        UnitList<?> units = thisCivilization.units().getUnitsAtLocation(location);
        AbstractUnit nextUnit = units.findUnitByUnitKind(unit.getUnitCategory());

        // no units of such type, so we can enter into city
        if (nextUnit == null) {
            return UnitMoveResult.SUCCESS_ENTERED_INTO_OWN_CITY;
        }

        return UnitMoveResult.FAIL_TILE_OCCUPIED;
    }

    private static UnitMoveResult checkUnitsSwap(AbstractUnit unit, Point nextLocation, boolean canSwapLocations) {
        UnitList<?> units = unit.getCivilization().units().getUnitsAtLocation(nextLocation);
        AbstractUnit nextUnit = units.findUnitByUnitKind(unit.getUnitCategory());
        if (nextUnit == null) {
            return UnitMoveResult.CHECK_FAILED;
        }

        // swapping must be the only dir on the route
        if (!canSwapLocations) {
            return UnitMoveResult.FAIL_SWAPPING_MUST_BE_THE_ONLY_MOVE;
        }

        // next unit must have enough passing score
        Point thisLocation = unit.getLocation();
        UnitMoveResult moveResult = checkCanMoveOnTile(nextUnit, thisLocation);
        if (!UnitMoveResult.CHECK_FAILED.equals(moveResult)) {
            return UnitMoveResult.FAIL_NOT_ENOUGH_PASSING_SCORE_TO_SWAP;
        }

        // looks good, let's swap them
        nextUnit.moveTo(thisLocation);
        return UnitMoveResult.SUCCESS_SWAPPED;
    }

    private static UnitMoveResult checkForeignTile(AbstractUnit unit, Point location) {
        Civilization thisCivilization = unit.getCivilization();
        Civilization nextCivilization = unit.getWorld().getCivilizationOnTile(location);
        if (nextCivilization == null || thisCivilization.equals(nextCivilization)) {
            return UnitMoveResult.CHECK_FAILED;
        }

        // check can we pass on it or not
        if (!nextCivilization.canCrossBorders(thisCivilization)) {
            return UnitMoveResult.FAIL_CANT_CROSS_BORDERS;
        }

        // skip the check so unit will be moved after this check
        return UnitMoveResult.CHECK_FAILED;
    }

    private static UnitMoveResult checkForeignCity(AbstractUnit unit, Point location) {
        // first, check is there a foreign city
        City city = unit.getWorld().getCityAtLocation(location);
        if (city == null) {
            return UnitMoveResult.CHECK_FAILED;
        }

        return UnitMoveResult.FAIL_TILE_OCCUPIED;
    }

    private static UnitMoveResult checkForeignUnits(AbstractUnit unit, Point location) {
        UnitList<?> units = unit.getWorld().getUnitsAtLocation(location, unit.getCivilization());
        if (units.isEmpty()) {
            return UnitMoveResult.CHECK_FAILED;
        }

        return UnitMoveResult.FAIL_TILE_OCCUPIED_BY_FOREIGN_UNIT;
    }

    public static UnitRoute findRoute(AbstractUnit unit, Point location) {
        UnitRoute unitRoute = new UnitRoute();
        if ((location == null) || (unit.getLocation().equals(location))) {
            return unitRoute;
        }

        // get the route from finish to start
        // Mountains have Integer.MAX_VALUE passing cost,
        // so here we use Integer.MAX_VALUE - 1
        LinkedList<AbstractDir> dirs = new LinkedList<>();
        Map<Point, DirPassScore> tileScores = getAllLocationsDirPassScore(unit, Integer.MAX_VALUE - 1, location);
        while (!location.equals(unit.getLocation())) {
            DirPassScore tileScore = tileScores.get(location);

            // there is no route at all
            if (tileScore == null) {
                return unitRoute;
            }
            AbstractDir dir = tileScore.getDir();
            dirs.addFirst(dir);

            // go back
            int tileY = tileScore.getTileY();
            AbstractDir inverseDir = dir.getInverse(tileY);
            location = unit.getTilesMap().addDirToLocation(location, inverseDir);
        }

        unitRoute.addAll(dirs);
        return unitRoute;
    }

    private static Map<Point, DirPassScore> getAllLocationsDirPassScore(AbstractUnit unit, int passScore, Point finishLocation) {
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

                    int nextPassScore = passScore - tileService.getPassCost(unit, nextTile);
                    if (nextPassScore >= 0) {
                        // check for foreign city located there
                        boolean isBlocked = false;
                        City city = unit.getWorld().getCityAtLocation(nextLocation);
                        if (city != null && !city.getCivilization().equals(unit.getCivilization())) {
                            isBlocked = true;
                        }

                        // check for units
                        UnitList<?> units = unit.getWorld().getUnitsAtLocation(nextLocation);
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
    private static Set<Point> getCandidatesToMove(AbstractUnit unit) {
        int passScore = unit.getPassScore();
        if (passScore == 0) {
            return Collections.emptySet();
        }

        Map<Point, DirPassScore> tileScores = getAllLocationsDirPassScore(unit, unit.getPassScore(), null);
        return tileScores.keySet();
    }

    // Find out tiles possible to move in
    public static Set<Point> getLocationsToMove(AbstractUnit unit) {
        Set<Point> locations = getCandidatesToMove(unit);

        // Exclude tiles occupied by
        // - other the same kind units from this civilization
        // - all foreign units
        // - all foreign cities
        UnitList<?> units = unit.getWorld().getUnitsAtLocations(locations);
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

    private static class DirPassScore {
        private final int passScore;
        private final AbstractDir dir;
        private final int tileY;

        DirPassScore(int passScore, AbstractDir dir, int tileY) {
            this.passScore = passScore;
            this.dir = dir;
            this.tileY = tileY;
        }

        public int getPassScore() {
            return passScore;
        }

        public AbstractDir getDir() {
            return dir;
        }

        public int getTileY() {
            return tileY;
        }
    }

    private static String getClientJSCode(AbstractUnit unit) {
        JsonBlock block = new JsonBlock('\'');
        block.startArray("locations");
        Set<Point> locations = getLocationsToMove(unit);
        for (Point loc : locations) {
            JsonBlock locBlock = new JsonBlock('\'');
            locBlock.addParam("col", loc.getX());
            locBlock.addParam("row", loc.getY());
            block.addElement(locBlock.getText());
        }
        block.stopArray();

        return String.format("client.moveUnitAction({ unit:'%1$s', ucol:'%2$s', urow:'%3$s', %4$s })",
                unit.getId(), unit.getLocation().getX(), unit.getLocation().getY(), block.getValue());
    }

    private static String getLocalizedName() {
        return L10nUnit.MOVE_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.MOVE_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(AbstractUnit unit) {
        if (canMove(unit).isFail()) {
            return null;
        }

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td><td>$actionDescription</td>",

            "$buttonOnClick", getClientJSCode(unit),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
