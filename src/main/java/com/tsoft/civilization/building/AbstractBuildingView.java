package com.tsoft.civilization.building;

import com.tsoft.civilization.improvement.city.action.DestroyBuildingAction;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.util.Format;

public abstract class AbstractBuildingView<B extends AbstractBuilding<?>> {
    public abstract String getLocalizedName();
    public abstract String getLocalizedDescription();
    public abstract String getStatusImageSrc();

    public StringBuilder getHtmlActions(B building) {
        StringBuilder destroyBuildingAction = DestroyBuildingAction.getHtml(building);
        if (destroyBuildingAction == null) {
            return null;
        }

        return Format.text(
            "<tr>$destroyBuildingAction</tr>",

            "$destroyBuildingAction", destroyBuildingAction
        );
    }
}
