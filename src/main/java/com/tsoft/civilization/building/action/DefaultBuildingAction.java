package com.tsoft.civilization.building.action;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.improvement.city.action.DestroyBuildingAction;
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
