package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nAction;
import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.building.L10nBuilding;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingActions;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.economic.Supply;

public class GetBuildingStatus extends AbstractAjaxRequest {

    private final BuildingActions buildingActions = new BuildingActions();
    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        // See buildings only for my civilization
        String buildingId = request.get("building");
        AbstractBuilding building = myCivilization.cities().getBuildingById(buildingId);
        if (building == null) {
            return Response.newErrorInstance(L10nBuilding.BUILDING_NOT_FOUND);
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
                <tr><td><button onclick="server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })">$civilizationName</button></td></tr>
                <tr><td><button onclick="client.getCityStatus({ col:'$cityCol', row:'$cityRow', city:'$city' })">$cityName</button></td></tr>
                <tr><td><img src='$buildingImage'/></td></tr>
            </table>
            """,

            "$buildingName", building.getView().getLocalizedName(),
            "$civilization", building.getCivilization().getId(),
            "$civilizationName", building.getCivilization().getView().getLocalizedCivilizationName(),
            "$cityCol", building.getCity().getLocation().getX(),
            "$cityRow", building.getCity().getLocation().getY(),
            "$city", building.getCity().getId(),
            "$cityName", building.getCity().getView().getLocalizedCityName(),
            "$buildingImage", building.getView().getStatusImageSrc()
        );
    }

    private StringBuilder getBuildingInfo(AbstractBuilding building) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(building.getCivilization())) {
            return null;
        }

        Supply supply = building.getSupply(building.getCity());
        return Format.text("""
            <table id='info_table'>
                <tr><td>$productionLabel</td><td>$production</td>
                <tr><td>$goldLabel</td><td>$gold</td>
                <tr><td>$foodLabel</td><td>$food</td>
                <tr><td>$happinessLabel</td><td>$happiness</td>
                <tr><td>$strengthLabel</td><td>$strength</td>
            </table>
            """,

            "$productionLabel", L10nCity.PRODUCTION, "$production", supply.getProduction(),
            "$goldLabel", L10nCity.GOLD, "$gold", supply.getGold(),
            "$foodLabel", L10nCity.FOOD, "$food", supply.getFood(),
            "$happinessLabel", L10nCity.HAPPINESS, "$happiness", supply.getHappiness(),
            "$strengthLabel", L10nCity.DEFENSE_STRENGTH, "$strength", building.getStrength()
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

            "$actions", L10nAction.AVAILABLE_ACTIONS,
            "$buildingActions", actions
        );
    }
}
