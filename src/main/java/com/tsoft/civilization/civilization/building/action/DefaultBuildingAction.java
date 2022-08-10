package com.tsoft.civilization.civilization.building.action;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.city.action.DestroyBuildingAction;
import com.tsoft.civilization.util.Format;

public class DefaultBuildingAction {

    public StringBuilder getHtmlActions(AbstractBuilding building) {
        StringBuilder destroyBuildingAction = DestroyBuildingAction.getHtml(building);
        if (destroyBuildingAction == null) {
            return null;
        }

        return Format.text("""
            <tr>$destroyBuildingAction</tr>
            """,

            "$destroyBuildingAction", destroyBuildingAction
        );
    }
}
