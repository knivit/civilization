package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.L10nTile;
import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.util.ContentType;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.civilization.Civilization;

public class GetTileStatus extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        Point location = myCivilization.getTilesMap().getLocation(request.get("col"), request.get("row"));
        if (location == null) {
            return Response.newErrorInstance(L10nWorld.INVALID_LOCATION);
        }

        AbstractTile tile = myCivilization.getTilesMap().getTile(location);

        StringBuilder value = Format.text(
            "$navigationPanel\n" +
            "$tileTitle\n" +
            "$tileInfo\n" +
            "$cityInfo\n" +
            "$units\n",

            "$navigationPanel", getNavigationPanel(),
            "$tileTitle", getTileTitle(myCivilization.getWorld(), tile),
            "$tileInfo", getTileInfo(tile),
            "$cityInfo", getCityInfo(myCivilization.getWorld(), tile),
            "$units", getUnitsInfo(tile.getLocation(), myCivilization.getWorld()));
        return new Response(ResponseCode.OK, value.toString(), ContentType.TEXT_HTML);
    }

    private StringBuilder getUnitsInfo(Point location, World world) {
        UnitCollection units = world.getUnitsAtLocation(location);
        if (units.isEmpty()) {
            return null;
        }

        Civilization myCivilization = getMyCivilization();
        StringBuilder buf = new StringBuilder();
        for (AbstractUnit unit : units) {
            if (myCivilization.equals(unit.getCivilization())) {
                buf.append(getMyUnitInfo(unit));
            } else {
                buf.append(getForeignUnitInfo(unit));
            }
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr><th colspan='2'>$header</th></tr>" +
                "$units" +
            "</table>",

            "$header", L10nUnit.UNITS_ON_TILE,
            "$units", buf
        );
    }

    private StringBuilder getMyUnitInfo(AbstractUnit unit) {
        return Format.text(
            "<tr>" +
                "<td><button onclick=\"client.getUnitStatus({ col:'$unitCol', row:'$unitRow', unit:'$unit' })\">$unitName</button></td>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })\">$civilizationName</button></td>" +
            "</tr>",

            "$unitCol", unit.getLocation().getX(),
            "$unitRow", unit.getLocation().getY(),
            "$unit", unit.getId(),
            "$unitName", unit.getView().getLocalizedName(),
            "$civilization", unit.getCivilization().getId(),
            "$civilizationName", unit.getCivilization().getView().getLocalizedCivilizationName()
        );
    }

    private StringBuilder getForeignUnitInfo(AbstractUnit unit) {
        return Format.text(
            "<tr>" +
                "<td>$unitName</td>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })\">$civilizationName</button></td>" +
            "</tr>",

            "$unitName", unit.getView().getLocalizedName(),
            "$civilization", unit.getCivilization().getId(),
            "$civilizationName", unit.getCivilization().getView().getLocalizedCivilizationName()
        );
    }

    private StringBuilder getCityInfo(World world, AbstractTile tile) {
        City city = world.getCityAtLocation(tile.getLocation());
        if (city == null) {
            return null;
        }

        StringBuilder buf;
        Civilization myCivilization = getMyCivilization();
        if (myCivilization.equals(city.getCivilization())) {
            buf = getMyCityInfo(city);
        } else {
            buf = getForeignCityInfo(city);
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr><th colspan='2'>$header</th></tr>" +
                "$city" +
            "</table>",

            "$header", L10nCity.CITY_ON_TILE,
            "$city", buf
        );
    }

    private StringBuilder getMyCityInfo(City city) {
        return Format.text(
            "<tr>" +
                "<td><button onclick=\"client.getCityStatus({ col:'$cityCol', row:'$cityRow', city:'$city' })\">$cityName</button></td>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })\">$civilizationName</button></td>" +
            "</tr>",

            "$city", city.getId(),
            "$cityCol", city.getLocation().getX(),
            "$cityRow", city.getLocation().getY(),
            "$cityName", city.getView().getLocalizedCityName(),
            "$civilization", city.getCivilization().getId(),
            "$civilizationName", city.getCivilization().getView().getLocalizedCivilizationName()
        );
    }

    private StringBuilder getForeignCityInfo(City city) {
        return Format.text(
            "<tr>" +
                "<td>$cityName</td>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })\">$civilizationName</button></td>" +
            "</tr>",

            "$cityName", city.getView().getLocalizedCityName(),
            "$civilization", city.getCivilization().getId(),
            "$civilizationName", city.getCivilization().getView().getLocalizedCivilizationName()
        );
    }

    private StringBuilder getTileTitle(World world, AbstractTile tile) {
        Civilization civilization = world.getCivilizationOnTile(tile.getLocation());
        if (civilization == null) {
            return null;
        }

        return Format.text(
            "<table id='title_table'>" +
                "<tr><td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })\">$civilizationName</button></td></tr>" +
                "<tr><td><img src='$civilizationImage'/></td></tr>" +
            "</table>",

            "$civilization", civilization.getId(),
            "$civilizationName", civilization.getView().getLocalizedCivilizationName(),
            "$civilizationImage", civilization.getView().getStatusImageSrc()
        );
    }

    // +-------+---+---+---+
    // |       | P | G | F |
    // +-------+---+-+-+---+
    // | Tile  | 0 | 0 | 0 |
    // | F1    | 0 | 0 | 0 |
    // | F2    | 0 | 0 | 0 |
    // +-------+---+---+---+
    // | Total | 0 | 0 | 0 |
    // +-------+---+---+---+
    private StringBuilder getTileInfo(AbstractTile tile) {
        TerrainFeature feature1 = (tile.getTerrainFeatures().size() > 0 ? tile.getTerrainFeatures().get(0) : null);
        TerrainFeature feature2 = (tile.getTerrainFeatures().size() > 1 ? tile.getTerrainFeatures().get(1) : null);

        return Format.text(
            "<table id='info_table'>" +
                "<tr><th></th><th>$productionLabel</th><th>$goldLabel</th><th>$foodLabel</th></tr>" +
                "<tr>" +
                    "<td><button onclick=\"server.sendAsyncAjax('ajax/GetTileInfo', { col:'$col', row:'$row' })\">$tileName</button></td>" +
                    "<td>$production</td>" +
                    "<td>$gold</td>" +
                    "<td>$food</td>" +
                "</tr>" +
                "$feature1\n" +
                "$feature2\n" +
                "$total\n" +
            "</table>",

            "$productionLabel", L10nTile.PRODUCTION,
            "$goldLabel", L10nTile.GOLD,
            "$foodLabel", L10nTile.FOOD,
            "$col", tile.getLocation().getX(),
            "$row", tile.getLocation().getY(),
            "$tileName", tile.getView().getLocalizedName(),
            "$production", tile.getBaseSupply().getProduction(),
            "$gold", tile.getBaseSupply().getGold(),
            "$food", tile.getBaseSupply().getFood(),

            "$feature1", getFeatureInfo(feature1),
            "$feature2", getFeatureInfo(feature2),
            "$total", getTotalInfo(tile, (feature1 != null || feature2 != null))
        );
    }

    private StringBuilder getFeatureInfo(TerrainFeature feature) {
        if (feature == null) {
            return null;
        }

        Supply featureSupply = feature.getSupply();
        return Format.text(
            "<tr>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetFeatureInfo', { feature:'$feature' })\">$featureName</button></td>" +
                "<td>$production</td>" +
                "<td>$gold</td>" +
                "<td>$food</td>" +
            "</tr>",

           "$feature", feature.getClassUuid(),
           "$featureName", feature.getView().getLocalizedName(),
           "$production", featureSupply.getProduction(),
           "$gold", featureSupply.getGold(),
           "$food", featureSupply.getFood()
        );
    }

    private StringBuilder getTotalInfo(AbstractTile tile, boolean isVisible) {
        if (!isVisible) {
            return null;
        }

        Supply supply = tile.getSupply();
        return Format.text(
            "<tr>" +
                "<td>$totalLabel</td>" +
                "<td>$production</td>" +
                "<td>$gold</td>" +
                "<td>$food</td>" +
            "</tr>",

            "$totalLabel", L10nTile.TOTAL,
            "$production", supply.getProduction(),
            "$gold", supply.getGold(),
            "$food", supply.getFood()
        );
    }
}
