package com.tsoft.civilization.civilization.building.action;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.city.ui.DestroyBuildingUi;
import com.tsoft.civilization.util.Format;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultBuildingAction {

    private final DestroyBuildingUi destroyBuildingUi;

    public static DefaultBuildingAction newInstance() {
        return new DefaultBuildingAction(DestroyBuildingUi.newInstance());
    }

    public StringBuilder getHtmlActions(AbstractBuilding building) {
        StringBuilder destroyBuildingHtml = destroyBuildingUi.getHtml(building);
        if (destroyBuildingHtml == null) {
            return null;
        }

        return Format.text("""
            <tr>$destroyBuildingHtml</tr>
            """,

            "$destroyBuildingHtml", destroyBuildingHtml
        );
    }
}
