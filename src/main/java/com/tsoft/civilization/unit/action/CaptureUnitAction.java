package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JsonBlock;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CaptureUnitAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CAN_CAPTURE = new ActionSuccessResult(L10nUnit.CAN_CAPTURE);
    public static final ActionSuccessResult FOREIGN_UNIT_CAPTURED = new ActionSuccessResult(L10nUnit.FOREIGN_UNIT_CAPTURED);

    public static final ActionFailureResult NO_LOCATIONS_TO_CAPTURE = new ActionFailureResult(L10nUnit.NO_LOCATIONS_TO_CAPTURE);
    public static final ActionFailureResult NOTHING_TO_CAPTURE = new ActionFailureResult(L10nUnit.NOTHING_TO_CAPTURE);
    public static final ActionFailureResult ATTACKER_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult INVALID_LOCATION = new ActionFailureResult(L10nWorld.INVALID_LOCATION);

    public static ActionAbstractResult capture(AbstractUnit attacker, Point location) {
        ActionAbstractResult result = canCapture(attacker);
        if (result.isFail()) {
            return result;
        }

        if (location == null) {
            return INVALID_LOCATION;
        }

        Collection<Point> locations = getLocationsToCapture(attacker);
        if (!locations.contains(location)) {
            return NOTHING_TO_CAPTURE;
        }

        AbstractUnit foreignUnit = getTargetToCaptureAtLocation(attacker, location);
        if (foreignUnit == null) {
            return NO_LOCATIONS_TO_CAPTURE;
        }

        // move unit
        attacker.moveTo(foreignUnit.getLocation());

        // capture foreign unit
        foreignUnit.capturedBy(attacker);

        return FOREIGN_UNIT_CAPTURED;
    }

    public static ActionAbstractResult canCapture(AbstractUnit attacker) {
        if (attacker == null || attacker.isDestroyed()) {
            return ATTACKER_NOT_FOUND;
        }

        Collection<Point> locations = getLocationsToCapture(attacker);
        if (locations.isEmpty()) {
            return NO_LOCATIONS_TO_CAPTURE;
        }

        return CAN_CAPTURE;
    }

    private static UnitList getTargetsToCapture(AbstractUnit capturer) {
        UnitList units = new UnitList();

        Collection<Point> locations = capturer.getTilesMap().getLocationsAround(capturer.getLocation(), 1);
        for (Point location : locations) {
            // check we can move to the location
            UnitMoveResult moveResult = MoveUnitAction.getMoveOnCaptureResult(capturer, location);
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

    public static AbstractUnit getTargetToCaptureAtLocation(AbstractUnit capturer, Point location) {
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

    public static List<Point> getLocationsToCapture(AbstractUnit unit) {
        UnitList units = getTargetsToCapture(unit);
        return units.getLocations();
    }

    private static String getClientJSCode(AbstractUnit unit) {
        JsonBlock block = new JsonBlock('\'');
        block.startArray("locations");
        List<Point> locations = getLocationsToCapture(unit);
        for (Point loc : locations) {
            JsonBlock locBlock = new JsonBlock('\'');
            locBlock.addParam("col", loc.getX());
            locBlock.addParam("row", loc.getY());
            block.addElement(locBlock.getText());
        }
        block.stopArray();

        return String.format("client.captureUnitAction({ attacker:'%1$s', ucol:'%2$s', urow:'%3$s', %4$s })",
                unit.getId(), unit.getLocation().getX(), unit.getLocation().getY(), block.getValue());
    }

    private static String getLocalizedName() {
        return L10nUnit.CAPTURE_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.CAPTURE_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(AbstractUnit attacker) {
        if (canCapture(attacker).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", getClientJSCode(attacker),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
