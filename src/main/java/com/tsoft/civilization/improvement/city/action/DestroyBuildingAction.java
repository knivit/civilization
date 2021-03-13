package com.tsoft.civilization.improvement.city.action;

import com.tsoft.civilization.building.L10nBuilding;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.building.settlement.Settlement;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

public class DestroyBuildingAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult destroyBuilding(AbstractBuilding building) {
        ActionAbstractResult result = canDestroyBuilding(building);
        if (result.isFail()) {
            return result;
        }

        building.remove();

        return CityActionResults.BUILDING_DESTROYED;
    }

    private static ActionAbstractResult canDestroyBuilding(AbstractBuilding building) {
        if (building == null || building.isDestroyed()) {
            return CityActionResults.BUILDING_NOT_FOUND;
        }

        if (Palace.CLASS_UUID.equals(building.getClassUuid()) || Settlement.CLASS_UUID.equals(building.getClassUuid())) {
            return CityActionResults.CANT_DESTROY_BUILDING;
        }

        return CityActionResults.CAN_DESTROY_BUILDING;
    }

    private static String getClientJSCode(AbstractBuilding building) {
        return String.format("client.destroyBuildingAction({ building:'%1$s' })", building.getId());
    }

    private static String getLocalizedName() {
        return L10nBuilding.DESTROY.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nBuilding.DESTROY_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(AbstractBuilding building) {
        if (canDestroyBuilding(building).isFail()) {
            return null;
        }

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td><td>$actionDescription</td>",

            "$buttonOnClick", getClientJSCode(building),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}