package com.tsoft.civilization.civilization.city.ui;

import com.tsoft.civilization.civilization.building.L10nBuilding;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.action.BuildBuildingAction;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.PRODUCTION_IMAGE;

@RequiredArgsConstructor
public class BuildBuildingUi {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final BuildBuildingAction buildBuildingAction;

    public StringBuilder getHtml(City city, String buildingClassUuid) {
        if (buildBuildingAction.canBuildBuilding(city, buildingClassUuid).isFail()) {
            return null;
        }

        return Format.text("""
            <button onclick="$buttonOnClick">$buttonLabel: $productionCost $productionImage</button>
            """,

            "$buttonOnClick", ClientAjaxRequest.buildBuildingAction(city, buildingClassUuid),
            "$buttonLabel", L10nBuilding.BUILD.getLocalized(),
            "$productionCost", city.getBuildingProductionCost(buildingClassUuid),
            "$productionImage", PRODUCTION_IMAGE
        );
    }
}
