package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.L10nTile;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.util.ContentType;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.world.Civilization;

public class GetMyCities extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text(
            "$navigationPanel\n" +
            "$civilizationInfo\n" +
            "$citiesInfo\n",

            "$navigationPanel", getNavigationPanel(),
            "$civilizationInfo", getCivilizationInfo(civilization),
            "$citiesInfo", getCitiesInfo(civilization));
        return new Response(ResponseCode.OK, value.toString(), ContentType.TEXT_HTML);
    }

    private StringBuilder getCivilizationInfo(Civilization civilization) {
        return Format.text(
            "<table id='title_table'>" +
                "<tr><td>$civilizationName</td></tr>" +
                "<tr><td><img src='$civilizationImage'/></td></tr>" +
            "</table>",

            "$civilizationName", civilization.getView().getLocalizedCivilizationName(),
            "$civilizationImage", civilization.getView().getStatusImageSrc()
        );
    }

    private StringBuilder getCitiesInfo(Civilization civilization) {
        CityCollection cities = new CityList(civilization.getCities());
        if (cities.isEmpty()) {
            return Format.text(
                "<table id='actions_table'><tr><th>$text</th></tr></table>",

                "$text", L10nCity.NO_CITIES
            );
        }

        cities.sortByName();

        StringBuilder buf = new StringBuilder();
        for (City city : cities) {
            buf.append(Format.text(
                "<tr>" +
                    "<td><button onclick=\"client.getCityStatus({ col:'$cityCol', row:'$cityRow', city:'$city' })\">$cityName</button></td>" +
                    "<td>$citizens</td>" +
                    "<td>$production</td>" +
                    "<td>$gold</td>" +
                    "<td>$food</td>" +
                "</tr>",

                "$citizens", city.getCitizenCount(),
                "$production", city.getCityScore().getProduction(),
                "$gold", city.getCityScore().getGold(),
                "$food", city.getCityScore().getFood(),
                "$cityCol", city.getLocation().getX(),
                "$cityRow", city.getLocation().getY(),
                "$city", city.getId(),
                "$cityName", city.getView().getLocalizedCityName()
            ));
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr>" +
                    "<th>$name</th>" +
                    "<th>$citizenHeader</th>" +
                    "<th>$productionHeader</th>" +
                    "<th>$goldHeader</th>" +
                    "<th>$foodHeader</th>" +
                "</tr>" +
                "$cities" +
            "</table>" +
            "<table id='legend_table'>" +
                "<tr><td>$citizenHeader</td><td>$citizenLegend</td></tr>" +
                "<tr><td>$productionHeader</td><td>$productionLegend</td></tr>" +
                "<tr><td>$goldHeader</td><td>$goldLegend</td></tr>" +
                "<tr><td>$foodHeader</td><td>$foodLegend</td></tr>" +
            "</table>",

            "$citizenHeader", L10nCity.CITIZEN_HEADER,
            "$productionHeader", L10nTile.PRODUCTION_HEADER,
            "$goldHeader", L10nTile.GOLD_HEADER,
            "$foodHeader", L10nTile.FOOD_HEADER,

            "$name", L10nCity.NAME,
            "$cities", buf,

            "$citizenLegend", L10nCity.CITIZEN,
            "$productionLegend", L10nTile.PRODUCTION,
            "$goldLegend", L10nTile.GOLD,
            "$foodLegend", L10nTile.FOOD
        );
    }
}
