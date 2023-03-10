package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.civilization.happiness.CivilizationHappinessService;
import com.tsoft.civilization.civilization.city.L10nCity;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.civilization.building.L10nBuilding;
import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.building.BuildingActions;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.*;

public class GetBuildingStatus extends AbstractAjaxRequest {

    private final BuildingActions buildingActions = new BuildingActions();
    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();

    public static StringBuilder getAjax(AbstractBuilding building) {
        return Format.text("server.sendAsyncAjax('ajax/GetBuildingStatus', { building:'$building' })",
            "$building", building.getId()
        );
    }

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        // See buildings only for my civilization
        String buildingId = request.get("building");
        AbstractBuilding building = myCivilization.getCityService().getBuildingById(buildingId);
        if (building == null) {
            return JsonResponse.badRequest(L10nBuilding.BUILDING_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $buildingTitle
            $buildingInfo
            $actions
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$buildingTitle", getBuildingTitle(building),
            "$buildingInfo", getBuildingInfo(building),
            "$actions", getActions(building));
        return HtmlResponse.ok(value);
    }

    private StringBuilder getBuildingTitle(AbstractBuilding building) {
        return Format.text("""
            <table id='title_table'>
                <tr><td>$buildingName</td></tr>
                <tr><td><button onclick="$getCivilizationStatus">$civilizationName</button></td></tr>
                <tr><td><button onclick="$getCityStatus">$cityName</button></td></tr>
                <tr><td><img src='$buildingImage'/></td></tr>
            </table>
            """,

            "$buildingName", building.getView().getLocalizedName(),
            "$getCivilizationStatus", GetCivilizationStatus.getAjax(building.getCivilization()),
            "$civilizationName", building.getCivilization().getView().getLocalizedCivilizationName(),
            "$getCityStatus", ClientAjaxRequest.getCityStatus(building.getCity()),
            "$cityName", building.getCity().getView().getLocalizedCityName(),
            "$buildingImage", building.getView().getStatusImageSrc()
        );
    }

    private StringBuilder getBuildingInfo(AbstractBuilding building) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(building.getCivilization())) {
            return null;
        }

        CivilizationHappinessService happinessService = building.getCivilization().getHappinessService();
        int happiness = happinessService.getHappiness().getTotal();
        Supply supply = building.calcIncomeSupply(myCivilization);
        return Format.text("""
            <table id='info_table'>
                <tr><td>$productionLabel</td><td>$production $productionImage</td>
                <tr><td>$goldLabel</td><td>$gold $goldImage</td>
                <tr><td>$foodLabel</td><td>$food $foodImage</td>
                <tr><td>$happinessLabel</td><td>$happiness $happinessImage</td>
            </table>
            """,

            "$productionLabel", L10nCity.PRODUCTION,
            "$production", supply.getProduction(),
            "$productionImage", PRODUCTION_IMAGE,
            "$goldLabel", L10nCity.GOLD,
            "$gold", supply.getGold(),
            "$goldImage", GOLD_IMAGE,
            "$foodLabel", L10nCity.FOOD,
            "$food", supply.getFood(),
            "$foodImage", FOOD_IMAGE,
            "$happinessLabel", L10nCity.HAPPINESS,
            "$happiness", happiness,
            "$happinessImage", HAPPINESS_IMAGE(happiness)
        );
    }

    private StringBuilder getActions(AbstractBuilding building) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(building.getCivilization())) {
            return null;
        }

        // nothing to do with a captured city
        if (building.isDestroyed()) {
            return Format.text("""
                <table id='actions_table'>
                    <tr><td>$captured</td></tr>
                </table>
                """,

                "$captured", L10nCity.CITY_WAS_CAPTURED
            );
        }

        StringBuilder actions = buildingActions.getHtmlActions(building);
        if (actions == null) {
            return null;
        }

        return Format.text("""
            <table id='actions_table'>
                <tr><th colspan='2'>$actions</th></tr>
                $buildingActions
            </table
            """,

            "$actions", L10nStatus.AVAILABLE_ACTIONS,
            "$buildingActions", actions
        );
    }
}
