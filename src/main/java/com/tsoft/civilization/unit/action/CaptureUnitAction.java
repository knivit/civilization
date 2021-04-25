package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JsonBlock;

import java.util.Collection;
import java.util.UUID;

public class CaptureUnitAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CAN_CAPTURE = new ActionSuccessResult(L10nUnit.CAN_CAPTURE);
    public static final ActionSuccessResult FOREIGN_UNIT_CAPTURED = new ActionSuccessResult(L10nUnit.FOREIGN_UNIT_CAPTURED);

    public static final ActionFailureResult NO_LOCATIONS_TO_CAPTURE = new ActionFailureResult(L10nUnit.NO_LOCATIONS_TO_CAPTURE);
    public static final ActionFailureResult NOTHING_TO_CAPTURE = new ActionFailureResult(L10nUnit.NOTHING_TO_CAPTURE);
    public static final ActionFailureResult ATTACKER_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult INVALID_LOCATION = new ActionFailureResult(L10nWorld.INVALID_LOCATION);

    private final UnitCaptureService unitCaptureService;

    public CaptureUnitAction(UnitCaptureService unitCaptureService) {
        this.unitCaptureService = unitCaptureService;
    }

    public ActionAbstractResult capture(AbstractUnit attacker, Point location) {
        ActionAbstractResult result = unitCaptureService.canCapture(attacker);
        if (result.isFail()) {
            return result;
        }

        if (location == null) {
            return INVALID_LOCATION;
        }

        Collection<Point> locations = unitCaptureService.getLocationsToCapture(attacker);
        if (!locations.contains(location)) {
            return NOTHING_TO_CAPTURE;
        }

        AbstractUnit foreignUnit = unitCaptureService.getTargetToCaptureAtLocation(attacker, location);
        if (foreignUnit == null) {
            return NO_LOCATIONS_TO_CAPTURE;
        }

        // move unit
        attacker.moveTo(foreignUnit.getLocation());

        // capture foreign unit
        foreignUnit.capturedBy(attacker);

        // passScore ends
        attacker.setPassScore(0);

        return FOREIGN_UNIT_CAPTURED;
    }

    private static String getLocalizedName() {
        return L10nUnit.CAPTURE_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.CAPTURE_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(AbstractUnit attacker) {
        if (unitCaptureService.canCapture(attacker).isFail()) {
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

    private StringBuilder getClientJSCode(AbstractUnit unit) {
        JsonBlock locations = new JsonBlock('\'');
        locations.startArray("locations");
        for (Point loc : unitCaptureService.getLocationsToCapture(unit)) {
            JsonBlock location = new JsonBlock('\'');
            location.addParam("col", loc.getX());
            location.addParam("row", loc.getY());
            locations.addElement(location.getText());
        }
        locations.stopArray();

        return ClientAjaxRequest.captureUnitAction(unit, locations);
    }
}
