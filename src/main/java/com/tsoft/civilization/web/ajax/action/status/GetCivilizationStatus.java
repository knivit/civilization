package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nAction;
import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.civilization.DeclareWarAction;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.util.ContentType;

import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

public class GetCivilizationStatus extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        // may be foreign civilization's id
        String civilizationId = request.get("civilization");
        Civilization civilization = myCivilization.getWorld().getCivilizationById(civilizationId);
        if (civilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text(
            "$navigationPanel\n" +
            "$civilizationTitle\n" +
            "$civilizationInfo\n" +
            "$actions\n" +
            "$units\n" +
            "$cities\n",

            "$navigationPanel", getNavigationPanel(),
            "$civilizationTitle", getCivilizationTitle(civilization),
            "$civilizationInfo", getCivilizationInfo(civilization),
            "$actions", getActions(myCivilization, civilization),
            "$units", getUnitsWithAvailableActions(myCivilization, civilization),
            "$cities", getCitiesWithAvailableActions(myCivilization, civilization));

        return new Response(ResponseCode.OK, value.toString(), ContentType.TEXT_HTML);
    }

    private StringBuilder getCivilizationTitle(Civilization civilization) {
        return Format.text(
            "<table id='title_table'>" +
                "<tr><td>$name</td></tr>" +
                "<tr><td><img src='$imageSrc'/></td></tr>" +
            "</table>",

            "$name", civilization.getView().getLocalizedCivilizationName(),
            "$imageSrc", civilization.getView().getStatusImageSrc()
        );
    }

    private StringBuilder getCivilizationInfo(Civilization civilization) {
        return Format.text(
            "<table id='info_table'>" +
                "<tr><th colspan='2'>$features</th></tr>" +
                "<tr><td>$populationLabel</td><td>$population</td>" +
                "<tr><td>$productionLabel</td><td>$production</td>" +
                "<tr><td>$goldLabel</td><td>$gold</td>" +
                "<tr><td>$foodLabel</td><td>$food</td>" +
                "<tr><td>$happinessLabel</td><td>$happiness</td>" +
                "<tr><td>$militaryUnitsLabel</td><td>$militaryUnits</td>" +
                "<tr><td>$civilUnitsLabel</td><td>$civilUnits</td>" +
                "<tr><td>$citiesLabel</td><td>$cities</td>" +
            "</table>",

            "$features", L10nCivilization.FEATURES,

            "$populationLabel", L10nCivilization.POPULATION, "$population", civilization.calcSupply().getPopulation(),
            "$productionLabel", L10nCivilization.PRODUCTION, "$production", civilization.calcSupply().getProduction(),
            "$goldLabel", L10nCivilization.GOLD, "$gold", civilization.calcSupply().getGold(),
            "$foodLabel", L10nCivilization.FOOD, "$food", civilization.calcSupply().getFood(),
            "$happinessLabel", L10nCivilization.HAPPINESS, "$happiness", civilization.calcSupply().getHappiness(),
            "$militaryUnitsLabel", L10nCivilization.MILITARY_UNITS_COUNT, "$militaryUnits", civilization.getUnits().getMilitaryCount(),
            "$civilUnitsLabel", L10nCivilization.CIVIL_UNITS_COUNT, "$civilUnits", civilization.getUnits().getCivilCount(),
            "$citiesLabel", L10nCivilization.CITIES_COUNT, "$cities", civilization.getCities().size()
        );
    }

    private StringBuilder getActions(Civilization myCivilization, Civilization civilization) {
        if (myCivilization.equals(civilization)) {
            return getInnerActions(myCivilization);
        }

        return getOuterActions(myCivilization, civilization);
    }

    private StringBuilder getInnerActions(Civilization myCivilization) {
        return null;
    }

    private StringBuilder getOuterActions(Civilization myCivilization, Civilization otherCivilization) {
        if (myCivilization.isWar(otherCivilization)) {
            return null;
        }

        return Format.text(
            "<table id='actions_table'><tr>" +
                "<tr><th colspan='2'>$actions</th></tr>" +
                "<tr>$declareWarAction</tr>" +
            "</table>",

            "$actions", L10nAction.AVAILABLE_ACTIONS,
            "$declareWarAction", DeclareWarAction.getHtml(myCivilization, otherCivilization)
        );
    }

    private StringBuilder getUnitsWithAvailableActions(Civilization myCivilization, Civilization civilization) {
        if (!myCivilization.equals(civilization)) {
            return null;
        }

        UnitCollection units = civilization.getUnitsWithActionsAvailable();
        if (units.isEmpty()) {
            return null;
        }

        units.sortByName();

        StringBuilder unitBuf = new StringBuilder();
        for (AbstractUnit unit : units) {
            unitBuf.append(Format.text(
                "<tr>" +
                    "<td><button onclick=\"client.getUnitStatus({ col:'$unitCol', row:'$unitRow', unit:'$unit' })\">$unitName</button></td>" +
                    "<td>$passScore</td>" +
                "</tr>",

                "$passScore", unit.getPassScore(),
                "$unitCol", unit.getLocation().getX(),
                "$unitRow", unit.getLocation().getY(),
                "$unit", unit.getId(),
                "$unitName", unit.getView().getLocalizedName()
            ));
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr><th>$availableUnits</th><th>$passScore</th></tr>" +
                "$units" +
            "</table>",

            "$availableUnits", L10nCivilization.AVAILABLE_UNITS,
            "$passScore", L10nUnit.PASS_SCORE,
            "$units", unitBuf
        );
    }

    private StringBuilder getCitiesWithAvailableActions(Civilization myCivilization, Civilization civilization) {
        if (!myCivilization.equals(civilization)) {
            return null;
        }

        CityCollection cities = civilization.getCitiesWithActionsAvailable();
        if (cities.isEmpty()) {
            return null;
        }

        cities.sortByName();

        StringBuilder citiesBuf = new StringBuilder();
        for (City city : cities) {
            citiesBuf.append(Format.text(
                "<tr>" +
                    "<td><button onclick=\"client.getCityStatus({ col:'$cityCol', row:'$cityRow', city:'$city' })\">$cityName</button></td>" +
                    "<td>$citizenCount</td>" +
                "</tr>",

                "$citizenCount", city.getCitizenCount(),
                "$cityCol", city.getLocation().getX(),
                "$cityRow", city.getLocation().getY(),
                "$city", city.getId(),
                "$cityName", city.getView().getLocalizedCityName()
            ));
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr><th>$availableCities</th><th>$citizens</th></tr>" +
                "$cities" +
            "</table>",

            "$citizens", L10nCity.CITIZEN,
            "$availableCities", L10nCivilization.AVAILABLE_CITIES,
            "$cities", citiesBuf
        );
    }
}
