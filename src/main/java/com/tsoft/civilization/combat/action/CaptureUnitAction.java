package com.tsoft.civilization.combat.action;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.combat.service.CaptureUnitService;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JsonBlock;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CaptureUnitAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final CaptureUnitService captureUnitService;

    public ActionAbstractResult capture(AbstractUnit attacker, Point location) {
        return captureUnitService.capture(attacker, location);
    }

    private static String getLocalizedName() {
        return L10nUnit.CAPTURE_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.CAPTURE_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(AbstractUnit attacker) {
        if (captureUnitService.canCapture(attacker).isFail()) {
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
        for (Point loc : captureUnitService.getLocationsToCapture(unit)) {
            JsonBlock location = new JsonBlock('\'');
            location.addParam("col", loc.getX());
            location.addParam("row", loc.getY());
            locations.addElement(location.getText());
        }
        locations.stopArray();

        return ClientAjaxRequest.captureUnitAction(unit, locations);
    }
}
