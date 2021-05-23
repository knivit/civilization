package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.building.L10nBuilding;
import com.tsoft.civilization.building.*;
import com.tsoft.civilization.improvement.city.construction.Construction;
import com.tsoft.civilization.improvement.city.construction.ConstructionList;
import com.tsoft.civilization.improvement.city.action.BuildBuildingAction;
import com.tsoft.civilization.improvement.city.action.BuildUnitAction;
import com.tsoft.civilization.improvement.city.action.BuyBuildingAction;
import com.tsoft.civilization.improvement.city.action.BuyUnitAction;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

import static com.tsoft.civilization.building.L10nBuilding.CONSTRUCTION_PRIORITY_LABEL;
import static com.tsoft.civilization.web.ajax.ServerStaticResource.*;

public class GetCityStatus extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String cityId = request.get("city");
        City city = myCivilization.getWorld().getCityById(cityId);
        if (city == null) {
            return JsonResponse.badRequest(L10nServer.CITY_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $cityTitle
            $cityBusinessInfo
            $cityCombatInfo
            $actions
            $constructionActions
            $buildings
            $constructionsInProgress
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$cityTitle", getCityTitle(city),
            "$cityBusinessInfo", getCityBusinessInfo(city),
            "$cityCombatInfo", getCityCombatInfo(city),
            "$actions", getActions(city),
            "$constructionActions", getConstructionActions(city),
            "$buildings", getBuildings(city),
            "$constructionsInProgress", getConstructions(city)
        );
        return HtmlResponse.ok(value);
    }

    private StringBuilder getCityTitle(City city) {
        return Format.text("""
            <table id='title_table'>
                <tr><td>$cityName</td></tr>
                <tr><td><button onclick="$getCivilizationStatus">$civilizationName</button></td></tr>
                <tr><td><image src='$cityImageSrc'/></td></tr>
            </table>
            """,

            "$cityName", city.getView().getLocalizedCityName(),
            "$getCivilizationStatus", GetCivilizationStatus.getAjax(city.getCivilization()),
            "$civilizationName", city.getCivilization().getView().getLocalizedCivilizationName(),
            "$cityImageSrc", city.getView().getStatusImageSrc()
        );
    }

    private StringBuilder getCityBusinessInfo(City city) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(city.getCivilization()) || city.isDestroyed()) {
            return null;
        }

        return Format.text("""
            <table id='info_table'>
                <tr><th colspan='2'>$features</th></tr>
                <tr><td>$populationLabel</td><td>$population $populationImage</td>
                <tr><td>$productionLabel</td><td>$production $productionImage</td>
                <tr><td>$goldLabel</td><td>$gold $goldImage</td>
                <tr><td>$foodLabel</td><td>$food $foodImage</td>
                <tr><td>$happinessLabel</td><td>$happiness $happinessImage</td>
                <tr><td>$unhappinessLabel</td><td>$unhappiness $unhappinessImage</td>
            </table>
            """,

            "$features", L10nCity.BUSINESS_FEATURES,

            "$populationLabel", L10nCity.POPULATION,
            "$population", city.getPopulationService().getCitizenCount(),
            "$populationImage", POPULATION_IMAGE,
            "$productionLabel", L10nCity.PRODUCTION,
            "$production", city.getSupply().getProduction(),
            "$productionImage", PRODUCTION_IMAGE,
            "$goldLabel", L10nCity.GOLD,
            "$gold", city.getSupply().getGold(),
            "$goldImage", GOLD_IMAGE,
            "$foodLabel", L10nCity.FOOD,
            "$food", city.getSupply().getFood(),
            "$foodImage", FOOD_IMAGE,
            "$happinessLabel", L10nCity.HAPPINESS,
            "$happiness", city.getHappiness().getTotal(),
            "$happinessImage", HAPPINESS_IMAGE(city.getHappiness().getTotal())
        );
    }

    private StringBuilder getCityCombatInfo(City city) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(city.getCivilization()) || city.isDestroyed()) {
            return null;
        }

        return Format.text("""
            <table id='info_table'>
                <tr><th colspan='2'>$features</th>
                <tr><td>$rangedAttackStrengthLabel</td><td>$rangedAttackStrength</td>
                <tr><td>$rangedAttackRadiusLabel</td><td>$rangedAttackRadius</td>
                <tr><td>$defenseStrengthLabel</td><td>$defenseStrength</td>
            </table>
            """,

            "$features", L10nCity.COMBAT_FEATURES,

            "$rangedAttackStrengthLabel", L10nUnit.RANGED_ATTACK_STRENGTH, "$rangedAttackStrength", city.calcCombatStrength().getRangedAttackStrength(),
            "$rangedAttackRadiusLabel", L10nUnit.RANGED_ATTACK_RADIUS, "$rangedAttackRadius", city.calcCombatStrength().getRangedAttackRadius(),
            "$defenseStrengthLabel", L10nUnit.STRENGTH, "$defenseStrength", city.calcCombatStrength().getDefenseStrength()
        );
    }

    private StringBuilder getActions(City city) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(city.getCivilization())) {
            return null;
        }

        if (city.isDestroyed()) {
            return Format.text("""
                <table id='actions_table'>
                    <tr><td>$text</td></tr>
                </table>
                """,

                "$text", L10nCity.CITY_WAS_CAPTURED
            );
        }


        StringBuilder buf = city.getView().getHtmlActions(city);
        if (buf == null) {
            return null;
        }

        return Format.text("""
            <table id='actions_table'>
                <tr><th colspan='2'>$header</th></tr>
                $actions
            </table>
            """,

            "$header", L10nStatus.AVAILABLE_ACTIONS,
            "$actions", buf
        );
    }

    // One item is
    // | Icon Name                      |
    // | Production Cost & Build button |
    // | Gold Cost & Buy button         |
    // | Description                    |
    private StringBuilder getConstructionActions(City city) {
        if (!city.canStartConstruction()) {
            return null;
        }

        return Format.text("""
            $constructUnitActions
            $constructBuildingActions
            """,

            "$constructUnitActions", getUnitConstructionActions(city),
            "$constructBuildingActions", getBuildingConstructionActions(city)
        );
    }

    private StringBuilder getUnitConstructionActions(City city) {
        StringBuilder buf = new StringBuilder();
        UnitList units = UnitFactory.getAvailableUnits(city.getCivilization());

        for (AbstractUnit unit : units.sortByName()) {
            StringBuilder buyUnitAction = BuildUnitAction.getHtml(city, unit.getClassUuid());
            StringBuilder buildUnitAction = BuyUnitAction.getHtml(city, unit.getClassUuid());

            if (buyUnitAction == null && buildUnitAction == null) {
                continue;
            }

            buf.append(Format.text("""
                <tr>
                    <td><button onclick="server.sendAsyncAjax('ajax/GetUnitInfo', { unit:'$unitUuid' })">$unitName</button></td>
                    <td>$buyUnitAction</td>
                    <td>$buildUnitAction</td>
                </tr>
                """,

                "$unitUuid", unit.getClassUuid(),
                "$unitName", unit.getView().getLocalizedName(),
                "$buyUnitAction", buyUnitAction,
                "$buildUnitAction", buildUnitAction));
        }

        if (buf.length() == 0) {
            return null;
        }

        return Format.text("""
            <table id='actions_table'>$header
            $units
            </table>
            """,

            "$header", getUnitConstructionHeader(),
            "$units", buf
        );
    }

    private StringBuilder getBuildingConstructionActions(City city) {
        StringBuilder buf = new StringBuilder();

        BuildingList buildings = BuildingFactory.getAvailableBuildings(city.getCivilization());
        for (AbstractBuilding building : buildings.sortByName()) {
            StringBuilder buyBuildingAction = BuildBuildingAction.getHtml(city, building.getClassUuid());
            StringBuilder buildBuildingAction = BuyBuildingAction.getHtml(city, building.getClassUuid());

            if (buyBuildingAction == null && buildBuildingAction == null) {
                continue;
            }

            buf.append(Format.text("""
                <tr>
                    <td><button onclick="server.sendAsyncAjax('ajax/GetBuildingInfo', { building:'$buildingUuid' })">$buildingName</button></td>
                    <td>$buyBuildingAction</td>
                    <td>$buildBuildingAction</td>
                </tr>
                """,

                "$buildingUuid", building.getClassUuid(),
                "$buildingName", building.getView().getLocalizedName(),
                "$buyBuildingAction", buyBuildingAction,
                "$buildBuildingAction", buildBuildingAction));
        }

        if (buf.length() == 0) {
            return null;
        }

        return Format.text("""
            <table id='actions_table'>
                $header
                $buildings
            </table>
            """,

            "$header", getBuildingConstructionHeader(),
            "$buildings", buf
        );
    }

    private StringBuilder getBuildingConstructionHeader() {
        return Format.text(
            "<tr><th colspan='3'>$header</th></tr>",
            "$header", L10nBuilding.BUILDINGS_TO_CONSTRUCT
        );
    }


    private StringBuilder getUnitConstructionHeader() {
        return Format.text(
            "<tr><th colspan='3'>$header</th></tr>",
            "$header", L10nBuilding.UNITS_TO_CONSTRUCT
        );
    }

    private StringBuilder getBuildings(City city) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(city.getCivilization())) {
            return null;
        }

        BuildingList buildings = city.getBuildings();
        if (buildings.isEmpty()) {
            return Format.text(
                "<table id='actions_table'><tr><th>$text</th></tr></table>",
                "$text", L10nBuilding.NO_BUILDINGS
            );
        }

        StringBuilder buf = new StringBuilder();
        for (AbstractBuilding building : buildings.sortByName()) {
            buf.append(Format.text("""
                <tr><td><button onclick="$getBuildingStatus">$buttonLabel</button></td></tr>
                """,

                "$getBuildingStatus", GetBuildingStatus.getAjax(building),
                "$buttonLabel", building.getView().getLocalizedName()
            ));
        }

        return Format.text("""
            <table id='actions_table'>
                <tr><th>$header</th></tr>
                $buildings
            </table>
            """,

            "$header", L10nBuilding.BUILDING_LIST,
            "$buildings", buf
        );
    }

    private StringBuilder getConstructions(City city) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(city.getCivilization())) {
            return null;
        }

        ConstructionList constructions = city.getConstructions();
        if (constructions.isEmpty()) {
            return Format.text("""
                <table id='actions_table'>
                    <tr>
                        <th>$text</th>
                    </tr>
                </table>
                """,

                "$text", L10nBuilding.NO_CONSTRUCTIONS
            );
        }

        StringBuilder buf = new StringBuilder();
        for (Construction construction : constructions) {
            buf.append(Format.text("""
                <tr>
                    <td><button onclick="$getConstructionStatus">$buttonLabel</button></td>
                </tr>
                """,

                "$getConstructionStatus", GetConstructionStatus.getAjax(construction),
                "$buttonLabel", construction.getView().getLocalizedName()
            ));
        }

        return Format.text("""
            <table id='actions_table'>
                <tr>
                    <th>$priorityLabel</th>
                    <th>$header</th>
                </tr>
                $constructions
            </table>
            """,

            "$priorityLabel", CONSTRUCTION_PRIORITY_LABEL,
            "$header", L10nBuilding.CONSTRUCTION_LIST,
            "$constructions", buf
        );
    }
}
