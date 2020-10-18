package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nAction;
import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.building.L10nBuilding;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.building.*;
import com.tsoft.civilization.improvement.city.action.BuildBuildingAction;
import com.tsoft.civilization.improvement.city.action.BuildUnitAction;
import com.tsoft.civilization.improvement.city.action.BuyBuildingAction;
import com.tsoft.civilization.improvement.city.action.BuyUnitAction;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

public class GetCityStatus extends AbstractAjaxRequest {
    private final BuildingListService buildingListService = new BuildingListService();

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String cityId = request.get("city");
        City city = myCivilization.getWorld().getCityById(cityId);
        if (city == null) {
            return Response.newErrorInstance(L10nServer.CITY_NOT_FOUND);
        }

        StringBuilder value = Format.text(
            "$navigationPanel\n" +
            "$cityTitle\n" +
            "$cityBusinessInfo\n" +
            "$cityCombatInfo\n" +
            "$actions\n" +
            "$constructionActions\n" +
            "$buildings\n",

            "$navigationPanel", getNavigationPanel(),
            "$cityTitle", getCityTitle(city),
            "$cityBusinessInfo", getCityBusinessInfo(city),
            "$cityCombatInfo", getCityCombatInfo(city),
            "$actions", getActions(city),
            "$constructionActions", getConstructionActions(city),
            "$buildings", getBuildings(city));
        return new HtmlResponse(ResponseCode.OK, value.toString());
    }

    private StringBuilder getCityTitle(City city) {
        return Format.text(
            "<table id='title_table'>" +
                "<tr><td>$cityName</td></tr>" +
                "<tr><td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })\">$civilizationName</button></td></tr>" +
                "<tr><td><image src='$cityImageSrc'/></td></tr>" +
            "</table>",

            "$cityName", city.getView().getLocalizedCityName(),
            "$civilization", city.getCivilization().getId(),
            "$civilizationName", city.getCivilization().getView().getLocalizedCivilizationName(),
            "$cityImageSrc", city.getView().getStatusImageSrc()
        );
    }

    private StringBuilder getCityBusinessInfo(City city) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(city.getCivilization()) || city.isDestroyed()) {
            return null;
        }

        return Format.text(
            "<table id='info_table'>" +
                "<tr><th colspan='2'>$features</th></tr>" +
                "<tr><td>$populationLabel</td><td>$population</td>" +
                "<tr><td>$productionLabel</td><td>$production</td>" +
                "<tr><td>$goldLabel</td><td>$gold</td>" +
                "<tr><td>$foodLabel</td><td>$food</td>" +
                "<tr><td>$happinessLabel</td><td>$happiness</td>" +
            "</table>",

            "$features", L10nCity.BUSINESS_FEATURES,

            "$populationLabel", L10nCity.POPULATION, "$population", city.getSupply().getPopulation(),
            "$productionLabel", L10nCity.PRODUCTION, "$production", city.getSupply().getProduction(),
            "$goldLabel", L10nCity.GOLD, "$gold", city.getSupply().getGold(),
            "$foodLabel", L10nCity.FOOD, "$food", city.getSupply().getFood(),
            "$happinessLabel", L10nCity.HAPPINESS, "$happiness", city.getSupply().getHappiness()
        );
    }

    private StringBuilder getCityCombatInfo(City city) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(city.getCivilization()) || city.isDestroyed()) {
            return null;
        }

        return Format.text(
            "<table id='info_table'>" +
                "<tr><th colspan='2'>$features</th>" +
                "<tr><td>$meleeAttackStrengthLabel</td><td>$meleeAttackStrength</td>" +
                "<tr><td>$canConquerCityLabel</td><td>$canConquerCity</td>" +
                "<tr><td>$attackExperienceLabel</td><td>$attackExperience</td>" +
                "<tr><td>$defenseExperienceLabel</td><td>$defenseExperience</td>" +
                "<tr><td>$rangedAttackStrengthLabel</td><td>$rangedAttackStrength</td>" +
                "<tr><td>$rangedAttackRadiusLabel</td><td>$rangedAttackRadius</td>" +
                "<tr><td>$strengthLabel</td><td>$strength</td>" +
            "</table>",

            "$features", L10nCity.COMBAT_FEATURES,

            "$meleeAttackStrengthLabel", L10nUnit.MELEE_ATTACK_STRENGTH, "$meleeAttackStrength", city.getCombatStrength().getMeleeAttackStrength(),
            "$canConquerCityLabel", L10nUnit.CAN_CONQUER_CITY, "$canConquerCity", city.getCombatStrength().canConquerCity(),
            "$attackExperienceLabel", L10nUnit.ATTACK_EXPERIENCE, "$attackExperience", city.getCombatStrength().getAttackExperience(),
            "$defenseExperienceLabel", L10nUnit.DEFENSE_EXPERIENCE, "$defenseExperience", city.getCombatStrength().getDefenseExperience(),
            "$rangedAttackStrengthLabel", L10nUnit.RANGED_ATTACK_STRENGTH, "$rangedAttackStrength", city.getCombatStrength().getRangedAttackStrength(),
            "$rangedAttackRadiusLabel", L10nUnit.RANGED_ATTACK_RADIUS, "$rangedAttackRadius", city.getCombatStrength().getRangedAttackRadius(),
            "$strengthLabel", L10nUnit.STRENGTH, "$strength", city.getCombatStrength().getStrength()
        );
    }

    private StringBuilder getActions(City city) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(city.getCivilization())) {
            return null;
        }

        if (city.isDestroyed()) {
            return Format.text(
                "<table id='actions_table'>" +
                    "<tr><td>$text</td></tr>" +
                "</table>",
                "$text", L10nCity.CITY_WAS_CAPTURED
            );
        }


        StringBuilder buf = city.getView().getHtmlActions(city);
        if (buf == null) {
            return null;
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr><th colspan='2'>$header</th></tr>" +
                "$actions" +
            "</table>",

            "$header", L10nAction.AVAILABLE_ACTIONS,
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

        return Format.text(
            "$constructUnitActions\n" +
            "$constructBuildingActions\n",

            "$constructUnitActions", getUnitConstructionActions(city),
            "$constructBuildingActions", getBuildingConstructionActions(city)
        );
    }

    private StringBuilder getUnitConstructionActions(City city) {
        StringBuilder buf = new StringBuilder();
        UnitList<?> units = UnitFactory.getAvailableUnits(city.getCivilization());
        for (AbstractUnit unit : units) {
            StringBuilder buyUnitAction = BuildUnitAction.getHtml(city, unit.getClassUuid());
            StringBuilder buildUnitAction = BuyUnitAction.getHtml(city, unit.getClassUuid());

            if (buyUnitAction == null && buildUnitAction == null) {
                continue;
            }

            buf.append(Format.text(
                "<tr>" +
                    "<td><button onclick=\"server.sendAsyncAjax('ajax/GetUnitInfo', { unit:'$unitUuid' })\">$unitName</button></td>" +
                    "<td>$buyUnitAction</td>" +
                    "<td>$buildUnitAction</td>" +
                "</tr>",

                "$unitUuid", unit.getClassUuid(),
                "$unitName", unit.getView().getLocalizedName(),
                "$buyUnitAction", buyUnitAction,
                "$buildUnitAction", buildUnitAction));
        }

        if (buf.length() == 0) {
            return null;
        }

        return Format.text(
            "<table id='actions_table'>" +
                "$header\n" +
                "$units\n" +
            "</table>",

            "$header", getUnitConstructionHeader(),
            "$units", buf
        );
    }

    private StringBuilder getBuildingConstructionActions(City city) {
        StringBuilder buf = new StringBuilder();

        BuildingList buildings = BuildingCatalog.values();
        for (AbstractBuilding building : buildings) {
            StringBuilder buyBuildingAction = BuildBuildingAction.getHtml(city, building.getClassUuid());
            StringBuilder buildBuildingAction = BuyBuildingAction.getHtml(city, building.getClassUuid());

            if (buyBuildingAction == null && buildBuildingAction == null) {
                continue;
            }

            buf.append(Format.text(
                "<tr>" +
                    "<td><button onclick=\"server.sendAsyncAjax('ajax/GetBuildingInfo', { building:'$buildingUuid' })\">$buildingName</button></td>" +
                    "<td>$buyBuildingAction</td>" +
                    "<td>$buildBuildingAction</td>" +
                "</tr>",

                "$buildingUuid", building.getClassUuid(),
                "$buildingName", building.getView().getLocalizedName(),
                "$buyBuildingAction", buyBuildingAction,
                "$buildBuildingAction", buildBuildingAction));
        }

        if (buf.length() == 0) {
            return null;
        }

        return Format.text(
            "<table id='actions_table'>" +
                "$header\n" +
                "$buildings" +
            "</table>",

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

        // sort the buildings by name
        buildings = buildingListService.sortByName(buildings);

        StringBuilder buf = new StringBuilder();
        for (AbstractBuilding building : buildings) {
            buf.append(Format.text(
                "<tr><td><button onclick=\"server.sendAsyncAjax('ajax/GetBuildingStatus', { building:'$building' })\">$buttonLabel</button></td></tr>",

                "$building", building.getId(),
                "$buttonLabel", building.getView().getLocalizedName()
            ));
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr><th>$header</th></tr>" +
                "$buildings" +
            "</table>",

            "$header", L10nBuilding.BUILDING_LIST,
            "$buildings", buf
        );
    }
}
