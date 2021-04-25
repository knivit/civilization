package com.tsoft.civilization.unit;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.action.UnitMoveResult;
import com.tsoft.civilization.util.Point;

import java.util.Collection;
import java.util.List;

import static com.tsoft.civilization.unit.action.CaptureUnitAction.*;

public class UnitCaptureService {

    private final UnitMoveService unitMoveService = new UnitMoveService();

    private UnitList getTargetsToCapture(AbstractUnit capturer) {
        UnitList units = new UnitList();

        Collection<Point> locations = capturer.getTilesMap().getLocationsAround(capturer.getLocation(), 1);
        for (Point location : locations) {
            // check we can move to the location
            UnitMoveResult moveResult = unitMoveService.getMoveOnCaptureResult(capturer, location);
            if (moveResult.isFailed()) {
                continue;
            }

            AbstractUnit foreignUnit = getTargetToCaptureAtLocation(capturer, location);
            if (foreignUnit != null) {
                units.add(foreignUnit);
            }
        }

        return units;
    }

    public AbstractUnit getTargetToCaptureAtLocation(AbstractUnit capturer, Point location) {
        UnitList foreignUnits = capturer.getWorld().getUnitsAtLocation(location, capturer.getCivilization());

        // if there are military units, protecting the civilians, then there is nothing to capture
        if (foreignUnits.size() != 1) {
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
}
