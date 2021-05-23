package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.civilization.action.DeclareWarAction;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Format;

import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.*;

public class GetCivilizationStatus extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();
    private final GetCivilizationInfo civilizationInfo = new GetCivilizationInfo();

    public static StringBuilder getAjax(Civilization civilization) {
        return Format.text("server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })",
            "$civilization", civilization.getId()
        );
    }

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        // may be foreign civilization's id
        String civilizationId = request.get("civilization");
        Civilization civilization = myCivilization.getWorld().getCivilizationById(civilizationId);
        if (civilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $civilizationInfo
            $civilizationStatus
            $actions
            $units
            $cities
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$civilizationInfo", civilizationInfo.getContent(civilization),
            "$civilizationStatus", getCivilizationStatus(civilization),
            "$actions", getActions(myCivilization, civilization),
            "$units", getUnitsWithAvailableActions(myCivilization, civilization),
            "$cities", getCitiesWithAvailableActions(myCivilization, civilization));

        return HtmlResponse.ok(value);
    }

    private StringBuilder getCivilizationStatus(Civilization civilization) {
        return Format.text("""
            <table id='info_table'>
                <tr><th colspan='3'>$features</th></tr>
                <tr><td>$populationImage</td><td>$populationLabel</td><td>$population</td>
                <tr><td>$productionImage</td><td>$productionLabel</td><td>$production</td>
                <tr><td>$goldImage</td><td>$goldLabel</td><td>$gold</td>
                <tr><td>$foodImage</td><td>$foodLabel</td><td>$food</td>
                <tr><td>$happinessImage</td><td>$happinessLabel</td><td>$happiness</td>
                <tr><td>$unhappinessImage</td><td>$unhappinessLabel</td><td>$unhappiness</td>
                <tr><td></td><td>$militaryUnitsLabel</td><td>$militaryUnits</td>
                <tr><td></td><td>$civilUnitsLabel</td><td>$civilUnits</td>
                <tr><td></td><td>$citiesLabel</td><td>$cities</td>
            </table>
            """,

            "$features", L10nCivilization.FEATURES,

            "$populationImage", POPULATION_IMAGE_WITH_TOOLTIP,
            "$populationLabel", L10nCivilization.POPULATION, "$population", civilization.getPopulationService().getCitizenCount(),
            "$productionImage", PRODUCTION_IMAGE_WITH_TOOLTIP,
            "$productionLabel", L10nCivilization.PRODUCTION, "$production", civilization.calcSupply().getProduction(),
            "$goldImage", GOLD_IMAGE_WITH_TOOLTIP,
            "$goldLabel", L10nCivilization.GOLD, "$gold", civilization.calcSupply().getGold(),
            "$foodImage", FOOD_IMAGE_WITH_TOOLTIP,
            "$foodLabel", L10nCivilization.FOOD, "$food", civilization.calcSupply().getFood(),
            "$happinessImage", HAPPINESS_IMAGE_WITH_TOOLTIP(civilization.calcHappiness().getTotal()),
            "$happinessLabel", L10nCivilization.HAPPINESS, "$happiness", civilization.calcHappiness().getTotal(),
            "$militaryUnitsLabel", L10nCivilization.MILITARY_UNITS_COUNT, "$militaryUnits", civilization.getUnitService().getMilitaryCount(),
            "$civilUnitsLabel", L10nCivilization.CIVIL_UNITS_COUNT, "$civilUnits", civilization.getUnitService().getCivilCount(),
            "$citiesLabel", L10nCivilization.CITIES_COUNT, "$cities", civilization.getCityService().size()
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

        return Format.text("""
            <table id='actions_table'><tr>
                <tr><th colspan='2'>$actions</th></tr>
                <tr>$declareWarAction</tr>
            </table>
            """,

            "$actions", L10nStatus.AVAILABLE_ACTIONS,
            "$declareWarAction", DeclareWarAction.getHtml(myCivilization, otherCivilization)
        );
    }

    private StringBuilder getUnitsWithAvailableActions(Civilization myCivilization, Civilization civilization) {
        if (!myCivilization.equals(civilization)) {
            return null;
        }

        UnitList units = civilization.getUnitService().getUnitsWithActionsAvailable();
        if (units.isEmpty()) {
            return null;
        }

        StringBuilder unitBuf = new StringBuilder();
        for (AbstractUnit unit : units.sortByName()) {
            unitBuf.append(Format.text("""
                <tr>
                    <td><button onclick="$getUnitStatus">$unitName</button></td>
                    <td>$passScore</td>
                </tr>
                """,

                "$getUnitStatus", ClientAjaxRequest.getUnitStatus(unit),
                "$unitName", unit.getView().getLocalizedName(),
                "$passScore", unit.getPassScore()
            ));
        }

        return Format.text("""
            <table id='actions_table'>
                <tr><th>$availableUnits</th><th>$passScore</th></tr>
                $units
            </table>
            """,

            "$availableUnits", L10nCivilization.AVAILABLE_UNITS,
            "$passScore", L10nUnit.PASS_SCORE,
            "$units", unitBuf
        );
    }

    private StringBuilder getCitiesWithAvailableActions(Civilization myCivilization, Civilization civilization) {
        if (!myCivilization.equals(civilization)) {
            return null;
        }

        CityList cities = civilization.getCityService().getCitiesWithAvailableActions();
        if (cities.isEmpty()) {
            return null;
        }

        StringBuilder citiesBuf = new StringBuilder();
        for (City city : cities.sortByName()) {
            citiesBuf.append(Format.text("""
                <tr>
                    <td><button onclick="$getCityStatus">$cityName</button></td>
                    <td>$citizenCount</td>
                </tr>
                """,

                "$getCityStatus", ClientAjaxRequest.getCityStatus(city),
                "$cityName", city.getView().getLocalizedCityName(),
                "$citizenCount", city.getCitizenCount()
            ));
        }

        return Format.text("""
            <table id='actions_table'>
                <tr><th>$availableCities</th><th>$citizens</th></tr>
                $cities
            </table>
            """,

            "$citizens", L10nCity.CITIZEN,
            "$availableCities", L10nCivilization.AVAILABLE_CITIES,
            "$cities", citiesBuf
        );
    }
}
