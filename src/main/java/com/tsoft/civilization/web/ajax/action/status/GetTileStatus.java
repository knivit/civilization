package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.civilization.tile.CivilizationTileSupplyService;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.civilization.city.L10nCity;
import com.tsoft.civilization.improvement.ImprovementList;
import com.tsoft.civilization.tile.feature.FeatureList;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.civilization.Civilization;

import static com.tsoft.civilization.civilization.L10nCivilization.*;
import static com.tsoft.civilization.web.ajax.ServerStaticResource.*;

public class GetTileStatus extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        Point location = myCivilization.getTilesMap().getLocation(request.get("col"), request.get("row"));
        if (location == null) {
            return JsonResponse.badRequest(L10nWorld.INVALID_LOCATION);
        }

        AbstractTerrain tile = myCivilization.getTilesMap().getTile(location);

        StringBuilder value = Format.text("""
            $navigationPanel
            $tileTitle
            $tileInfo
            $cityInfo
            $units
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$tileTitle", getTileTitle(myCivilization.getWorld(), tile),
            "$tileInfo", getTileInfo(myCivilization, tile),
            "$cityInfo", getCityInfo(myCivilization.getWorld(), tile),
            "$units", getUnitsInfo(tile.getLocation(), myCivilization.getWorld()));
        return HtmlResponse.ok(value);
    }

    private StringBuilder getUnitsInfo(Point location, World world) {
        UnitList units = world.getUnitsAtLocation(location);
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

        return Format.text("""
            <table id='actions_table'>
                <tr><th colspan='2'>$header</th></tr>
                $units
            </table>
            """,

            "$header", L10nUnit.UNITS_ON_TILE,
            "$units", buf
        );
    }

    private StringBuilder getMyUnitInfo(AbstractUnit unit) {
        return Format.text("""
            <tr>
                <td><button onclick="$getUnitStatus">$unitName</button></td>
                <td><button onclick="$getCivilizationStatus">$civilizationName</button></td>
            </tr>
            """,

            "$getUnitStatus", ClientAjaxRequest.getUnitStatus(unit),
            "$unitName", unit.getView().getLocalizedName(),
            "$getCivilizationStatus", GetCivilizationStatus.getAjax(unit.getCivilization()),
            "$civilizationName", unit.getCivilization().getView().getLocalizedCivilizationName()
        );
    }

    private StringBuilder getForeignUnitInfo(AbstractUnit unit) {
        return Format.text("""
            <tr>
                <td>$unitName</td>
                <td><button onclick="$getCivilizationStatus">$civilizationName</button></td>
            </tr>
            """,

            "$unitName", unit.getView().getLocalizedName(),
            "$getCivilizationStatus", GetCivilizationStatus.getAjax(unit.getCivilization()),
            "$civilizationName", unit.getCivilization().getView().getLocalizedCivilizationName()
        );
    }

    private StringBuilder getCityInfo(World world, AbstractTerrain tile) {
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

        return Format.text("""
            <table id='actions_table'>
                <tr><th colspan='2'>$header</th></tr>
                $city
            </table>
            """,

            "$header", L10nCity.CITY_ON_TILE,
            "$city", buf
        );
    }

    private StringBuilder getMyCityInfo(City city) {
        return Format.text("""
            <tr>
                <td><button onclick="$getCityStatus">$cityName</button></td>
                <td><button onclick="$getCivilizationStatus">$civilizationName</button></td>
            </tr>
            """,

            "$getCityStatus", ClientAjaxRequest.getCityStatus(city),
            "$cityName", city.getView().getLocalizedCityName(),
            "$getCivilizationStatus", GetCivilizationStatus.getAjax(city.getCivilization()),
            "$civilizationName", city.getCivilization().getView().getLocalizedCivilizationName()
        );
    }

    private StringBuilder getForeignCityInfo(City city) {
        return Format.text("""
            <tr>
                <td>$cityName</td>
                <td><button onclick="$getCivilizationStatus">$civilizationName</button></td>
            </tr>
            """,

            "$cityName", city.getView().getLocalizedCityName(),
            "$getCivilizationStatus", GetCivilizationStatus.getAjax(city.getCivilization()),
            "$civilizationName", city.getCivilization().getView().getLocalizedCivilizationName()
        );
    }

    private StringBuilder getTileTitle(World world, AbstractTerrain tile) {
        Civilization civilization = world.getCivilizationOnTile(tile.getLocation());
        if (civilization == null) {
            return null;
        }

        return Format.text("""
            <table id='title_table'>
                <tr><td><button onclick="$getCivilizationStatus">$civilizationName</button></td></tr>
                <tr><td><img src='$civilizationImage'/></td></tr>
            </table>
            """,

            "$getCivilizationStatus", GetCivilizationStatus.getAjax(civilization),
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
    private StringBuilder getTileInfo(Civilization civilization, AbstractTerrain tile) {
        return Format.text("""
            <table id='info_table'>
                <tr>
                    <th></th>
                    <th>$productionImage $productionLabel</th>
                    <th>$goldImage $goldLabel</th>
                    <th>$foodImage $foodLabel</th>
                </tr>
                <tr>
                    <td><button onclick="$getTileInfo">$tileName</button></td>
                    <td>$production</td>
                    <td>$gold</td>
                    <td>$food</td>
                </tr>
                $feature
                $improvement
                $total
            </table>
            """,

            "$productionImage", PRODUCTION_IMAGE,
            "$productionLabel", PRODUCTION,
            "$goldImage", GOLD_IMAGE,
            "$goldLabel", GOLD,
            "$foodImage", FOOD_IMAGE,
            "$foodLabel", FOOD,
            "$getTileInfo", GetTileInfo.getAjax(tile),
            "$tileName", tile.getView().getLocalizedName(),
            "$production", tile.getBaseSupply().getProduction(),
            "$gold", tile.getBaseSupply().getGold(),
            "$food", tile.getBaseSupply().getFood(),

            "$feature", getFeatureInfo(tile.getFeatures()),
            "$improvement", getImprovementInfo(civilization, tile.getImprovements()),
            "$total", getTotalInfo(civilization, tile)
        );
    }

    private StringBuilder getImprovementInfo(Civilization civilization, ImprovementList improvements) {
        if (improvements == null || improvements.isEmpty()) {
            return null;
        }

        StringBuilder buf = new StringBuilder();
        for (AbstractImprovement improvement : improvements) {
            Supply baseSupply = improvement.getBaseSupply(civilization);

            buf.append(Format.text("""
                <tr>
                    <td><button onclick="$getImprovementInfo">$improvementName</button></td>
                    <td>$production</td>
                    <td>$gold</td>
                    <td>$food</td>
                </tr>
                """,

                "$getImprovementInfo", GetImprovementInfo.getAjax(improvement),
                "$improvementName", improvement.getView().getLocalizedName(),
                "$production", baseSupply.getProduction(),
                "$gold", baseSupply.getGold(),
                "$food", baseSupply.getFood()
            ));
        }

        return buf;
    }

    private StringBuilder getFeatureInfo(FeatureList features) {
        if (features == null || features.isEmpty()) {
            return null;
        }

        StringBuilder buf = new StringBuilder();
        for (AbstractFeature feature : features) {
            Supply featureSupply = feature.getSupply();

            buf.append(Format.text("""
                <tr>
                    <td><button onclick="$getFeatureInfo">$featureName</button></td>
                    <td>$production</td>
                    <td>$gold</td>
                    <td>$food</td>
                </tr>
                """,

                "$getFeatureInfo", GetFeatureInfo.getAjax(feature),
                "$featureName", feature.getView().getLocalizedName(),
                "$production", featureSupply.getProduction(),
                "$gold", featureSupply.getGold(),
                "$food", featureSupply.getFood()
            ));
        }

        return buf;
    }

    private StringBuilder getTotalInfo(Civilization civilization, AbstractTerrain tile) {
        CivilizationTileSupplyService tileSupplyService = new CivilizationTileSupplyService(civilization);

        Supply supply = tileSupplyService.calcIncomeSupply(tile.getLocation());

        return Format.text("""
            <tr>
                <td>$totalLabel</td>
                <td>$production</td>
                <td>$gold</td>
                <td>$food</td>
            </tr>
            """,

            "$totalLabel", L10nTile.TOTAL,
            "$production", supply.getProduction(),
            "$gold", supply.getGold(),
            "$food", supply.getFood()
        );
    }
}
