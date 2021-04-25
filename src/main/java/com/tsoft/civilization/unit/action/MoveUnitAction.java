package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MoveUnitAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult UNIT_MOVED = new ActionSuccessResult(L10nUnit.UNIT_MOVED);
    public static final ActionSuccessResult CAN_MOVE = new ActionSuccessResult(L10nUnit.CAN_MOVE);

    public static final ActionFailureResult UNIT_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult INVALID_LOCATION = new ActionFailureResult(L10nUnit.INVALID_LOCATION);
    public static final ActionFailureResult NO_LOCATIONS_TO_MOVE = new ActionFailureResult(L10nUnit.NO_LOCATIONS_TO_MOVE);

    private final UnitMoveService unitMoveService;

    public MoveUnitAction(UnitMoveService unitMoveService) {
        this.unitMoveService = unitMoveService;
    }

    public ActionAbstractResult move(AbstractUnit unit, Point location) {
        if (location == null) {
            return INVALID_LOCATION;
        }

        ActionAbstractResult result = canMove(unit);
        if (result.isFail()) {
            return result;
        }

        UnitRoute route = unitMoveService.findRoute(unit, location);
        ArrayList<UnitMoveResult> results = unitMoveService.moveByRoute(unit, route);
        for (UnitMoveResult moveResult : results) {
            if (moveResult.isFailed()) {
                return INVALID_LOCATION;
            }
        }

        return UNIT_MOVED;
    }

    public ActionAbstractResult canMove(AbstractUnit unit) {
        if (unit == null || unit.isDestroyed() || unit.getLocation() == null) {
            return UNIT_NOT_FOUND;
        }

        Set<Point> locations = unitMoveService.getLocationsToMove(unit);
        if (locations.size() == 0) {
            return NO_LOCATIONS_TO_MOVE;
        }

        return CAN_MOVE;
    }

    private static String getLocalizedName() {
        return L10nUnit.MOVE_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.MOVE_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(AbstractUnit unit) {
        if (canMove(unit).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", getClientJSCode(unit),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }

    private StringBuilder getClientJSCode(AbstractUnit unit) {
        JsonBlock locations = new JsonBlock('\'');
        locations.startArray("locations");
        for (Point loc : unitMoveService.getLocationsToMove(unit)) {
            JsonBlock location = new JsonBlock('\'');
            location.addParam("col", loc.getX());
            location.addParam("row", loc.getY());
            locations.addElement(location.getText());
        }
        locations.stopArray();

        return ClientAjaxRequest.moveUnitAction(unit, locations);
    }
}
