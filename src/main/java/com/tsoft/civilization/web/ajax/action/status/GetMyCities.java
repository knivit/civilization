package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.improvement.city.CityListService;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.*;

public class GetMyCities extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();
    private final CityListService cityListService = new CityListService();

    public static StringBuilder getAjax() {
        return new StringBuilder("server.sendAsyncAjax('ajax/GetMyCities')");
    }

    @Override
    public Response getJson(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $civilizationInfo
            $citiesInfo
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$civilizationInfo", getCivilizationInfo(civilization),
            "$citiesInfo", getCitiesInfo(civilization));
        return HtmlResponse.ok(value);
    }

    private StringBuilder getCivilizationInfo(Civilization civilization) {
        return Format.text("""
            <table id='title_table'>
                <tr><td>$civilizationName</td></tr>
                <tr><td><img src='$civilizationImage'/></td></tr>
            </table>
            """,

            "$civilizationName", civilization.getView().getLocalizedCivilizationName(),
            "$civilizationImage", civilization.getView().getStatusImageSrc()
        );
    }

    private StringBuilder getCitiesInfo(Civilization civilization) {
        CityList cities = civilization.cities().getCities();
        if (cities.isEmpty()) {
            return Format.text("""
                <table id='actions_table'><tr><th>$text</th></tr></table>
                """,

                "$text", L10nCity.NO_CITIES
            );
        }

        cities = cityListService.sortByName(cities);

        StringBuilder buf = new StringBuilder();
        for (City city : cities) {
            buf.append(Format.text("""
                <tr>
                    <td><button onclick="$getCityStatus">$cityName</button></td>
                    <td>$citizens</td>
                    <td>$production</td>
                    <td>$gold</td>
                    <td>$food</td>
                </tr>
                """,

                "$getCityStatus", ClientAjaxRequest.getCityStatus(city),
                "$cityName", city.getView().getLocalizedCityName(),
                "$citizens", city.getCitizenCount(),
                "$production", city.getSupply().getProduction(),
                "$gold", city.getSupply().getGold(),
                "$food", city.getSupply().getFood()
            ));
        }

        return Format.text("""
            <table id='actions_table'>
                <tr>
                    <th>$name</th>
                    <th><image src='$populationImage'/></th>
                    <th><image src='$productionImage'/></th>
                    <th><image src='$goldImage'/></th>
                    <th><image src='$foodImage'/></th>
                </tr>
                $cities
            </table>
            """,

            "$name", L10nCity.NAME,
            "$populationImage", POPULATION_IMAGE,
            "$productionImage", PRODUCTION_IMAGE,
            "$goldImage", GOLD_IMAGE,
            "$foodImage", FOOD_IMAGE,
            "$cities", buf
        );
    }
}
