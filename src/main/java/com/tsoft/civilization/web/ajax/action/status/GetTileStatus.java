package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.civilization.Civilization;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.*;

public class GetTileStatus extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();
    private static final TileService tileService = new TileService();

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

        AbstractTile tile = myCivilization.getTilesMap().getTile(location);

        StringBuilder value = Format.text("""
            $navigationPanel
            $tileTitle
            $tileInfo
            $cityInfo
            $units
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$tileTitle", getTileTitle(myCivilization.getWorld(), tile),
            "$tileInfo", getTileInfo(tile),
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

    private StringBuilder getTileTitle(World world, AbstractTile tile) {
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
    private StringBuilder getTileInfo(AbstractTile tile) {
        TerrainFeature feature1 = (tile.getTerrainFeatures().size() > 0 ? tile.getTerrainFeatures().get(0) : null);
        TerrainFeature feature2 = (tile.getTerrainFeatures().size() > 1 ? tile.getTerrainFeatures().get(1) : null);

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
                $feature1
                $feature2
                $total
            </table>
            """,

            "$productionImage", PRODUCTION_IMAGE,
            "$productionLabel", L10nTile.PRODUCTION,
            "$goldImage", GOLD_IMAGE,
            "$goldLabel", L10nTile.GOLD,
            "$foodImage", FOOD_IMAGE,
            "$foodLabel", L10nTile.FOOD,
            "$getTileInfo", GetTileInfo.getAjax(tile),
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
        return Format.text("""
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
        );
    }

    private StringBuilder getTotalInfo(AbstractTile tile, boolean isVisible) {
        if (!isVisible) {
            return null;
        }

        Supply supply = tileService.calcSupply(tile);
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
