package com.tsoft.civilization.civilization.city.ui;

import com.tsoft.civilization.civilization.city.action.BuildUnitAction;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.PRODUCTION_IMAGE;

@RequiredArgsConstructor
public class BuildUnitUi {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final BuildUnitAction buildUnitAction;

    private static String getLocalizedName() {
        return L10nUnit.BUILD.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.BUILD_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(City city, String unitClassUuid) {
        if (buildUnitAction.canBuildUnit(city, unitClassUuid).isFail()) {
            return null;
        }

        return Format.text("""
            <button onclick="$buttonOnClick">$buttonLabel: $productionCost $productionImage</button>
            """,

            "$buttonOnClick", ClientAjaxRequest.buildUnitAction(city, unitClassUuid),
            "$buttonLabel", getLocalizedName(),
            "$productionCost", city.getUnitProductionCost(unitClassUuid),
            "$productionImage", PRODUCTION_IMAGE
        );
    }
}
