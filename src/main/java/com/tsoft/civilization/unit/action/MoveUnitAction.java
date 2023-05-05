package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.service.move.MoveUnitService;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;

import java.util.UUID;

public class MoveUnitAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final MoveUnitService moveService;

    public MoveUnitAction(MoveUnitService moveService) {
        this.moveService = moveService;
    }

    public ActionAbstractResult move(AbstractUnit unit, Point location) {
        return moveService.move(unit, location);
    }

    private static String getLocalizedName() {
        return L10nUnit.MOVE_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.MOVE_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(AbstractUnit unit) {
        if (moveService.checkCanMove(unit).isFail()) {
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
        for (Point loc : moveService.getLocationsToMove(unit)) {
            JsonBlock location = new JsonBlock('\'');
            location.addParam("col", loc.getX());
            location.addParam("row", loc.getY());
            locations.addElement(location.getText());
        }
        locations.stopArray();

        return ClientAjaxRequest.moveUnitAction(unit, locations);
    }
}
