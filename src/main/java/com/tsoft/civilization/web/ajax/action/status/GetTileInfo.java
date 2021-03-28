package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.tile.L10nTile;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.base.TilePassCostTable;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.tile.base.AbstractTileView;
import com.tsoft.civilization.civilization.Civilization;

public class GetTileInfo extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();
    private static final TileService tileService = new TileService();

    @Override
    public Response getJson(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        Point location = civilization.getTilesMap().getLocation(request.get("col"), request.get("row"));
        if (location == null) {
            return JsonResponse.badRequest(L10nWorld.INVALID_LOCATION);
        }

        AbstractTile tile = civilization.getTilesMap().getTile(location);

        StringBuilder value = Format.text("""
            $navigationPanel
            $tileInfo
            $tileDetails
            $tilePassInfo
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$tileInfo", getTileInfo(tile),
            "$tileDetails", getTileDetailInfo(tile),
            "$tilePassInfo", getTilePassInfo(tile, civilization)
        );
        return HtmlResponse.ok(value);
    }

    private StringBuilder getTileInfo(AbstractTile tile) {
        AbstractTileView view = tile.getView();
        return Format.text("""
            <table id='title_table'>
                <tr><td>$name</td></tr>
                <tr><td><img src='$image'/></td></tr>
                <tr><td>$description</td></tr>
            </table>
            """,

            "$name", view.getLocalizedName(),
            "$image", view.getStatusImageSrc(),
            "$description", view.getLocalizedDescription()
        );
    }

    private StringBuilder getTileDetailInfo(AbstractTile tile) {
        Supply tileSupply = tile.getBaseSupply();
        return Format.text("""
            <table id='info_table'>
                <tr><th colspan='2'>$features</th></tr>
                <tr><td>$productionLabel</td><td>$production</td></tr>
                <tr><td>$goldLabel</td><td>$gold</td></tr>
                <tr><td>$foodLabel</td><td>$food</td></tr>
                <tr><td>$canBuildCityLabel</td><td>$canBuildCity</td></tr>
                <tr><td>$defenseBonusLabel</td><td>$defenseBonus</td></tr>
            </table>
            """,

            "$features", L10nTile.FEATURES,

            "$productionLabel", L10nTile.PRODUCTION,
            "$production", tileSupply.getProduction(),
            "$goldLabel", L10nTile.GOLD,
            "$gold", tileSupply.getGold(),
            "$foodLabel", L10nTile.FOOD,
            "$food", tileSupply.getFood(),
            "$canBuildCityLabel", L10nTile.CAN_BUILD_CITY,
            "$canBuildCity", (tile.canBuildCity() ? L10nTile.YES : L10nTile.NO),
            "$defenseBonusLabel", L10nTile.DEFENSE_BONUS,
            "$defenseBonus", tile.getDefensiveBonusPercent()
        );
    }

    private StringBuilder getTilePassInfo(AbstractTile tile, Civilization civilization) {
        StringBuilder buf = new StringBuilder();
        UnitList units = civilization.units().getUnits();
        for (AbstractUnit unit : units) {
            int passCost = tileService.getPassCost(unit, tile);
            buf.append(Format.text(
                "<tr>" +
                    "<td><button onclick=\"client.getUnitStatus({ col:'$unitCol', row:'$unitRow', unit:'$unit' })\">$unitName</button></td>" +
                    "<td>$passCost</td>" +
                "</tr>",

                "$unitCol", unit.getLocation().getX(),
                "$unitRow", unit.getLocation().getY(),
                "$unit", unit.getId(),
                "$unitName", unit.getView().getLocalizedName(),
                "$passCost", (passCost == TilePassCostTable.UNPASSABLE ? L10nTile.TILE_IS_UNPASSABLE : passCost)
            ));
        }

        return Format.text("""
            <table id='actions_table'>
                <tr><th colspan='2'>$passCostHeader</th></tr>
                $units
            </table>
            """,

            "$passCostHeader", L10nTile.PASS_COST,
            "$units", buf
        );
    }
}
