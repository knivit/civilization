package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.L10nTile;
import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.tile.util.TilePassCostTable;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.world.economic.TileSupply;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.web.util.ContentType;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.view.tile.base.AbstractTileView;
import com.tsoft.civilization.world.Civilization;

public class GetTileInfo extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        Point location = civilization.getTilesMap().getLocation(request.get("col"), request.get("row"));
        if (location == null) {
            return Response.newErrorInstance(L10nWorld.INVALID_LOCATION);
        }

        AbstractTile tile = civilization.getTilesMap().getTile(location);

        StringBuilder value = Format.text(
            "$navigationPanel\n" +
            "$tileInfo\n" +
            "$tileDetails\n" +
            "$tilePassInfo\n",

            "$navigationPanel", getNavigationPanel(),
            "$tileInfo", getTileInfo(tile),
            "$tileDetails", getTileDetailInfo(tile),
            "$tilePassInfo", getTilePassInfo(tile, civilization)
        );
        return new Response(ResponseCode.OK, value.toString(), ContentType.TEXT_HTML);
    }

    private StringBuilder getTileInfo(AbstractTile tile) {
        AbstractTileView view = tile.getView();
        return Format.text(
            "<table id='title_table'>" +
                "<tr><td>$name</td></tr>" +
                "<tr><td><img src='$image'/></td></tr>" +
                "<tr><td>$description</td></tr>" +
            "</table>",

            "$name", view.getLocalizedName(),
            "$image", view.getStatusImageSrc(),
            "$description", view.getLocalizedDescription()
        );
    }

    private StringBuilder getTileDetailInfo(AbstractTile tile) {
        TileSupply tileSupply = tile.getBaseSupply();
        return Format.text(
            "<table id='info_table'>" +
                "<tr><th colspan='2'>$features</th></tr>" +
                "<tr><td>$productionLabel</td><td>$production</td></tr>" +
                "<tr><td>$goldLabel</td><td>$gold</td></tr>" +
                "<tr><td>$foodLabel</td><td>$food</td></tr>" +
                "<tr><td>$canBuildCityLabel</td><td>$canBuildCity</td></tr>" +
                "<tr><td>$defenseBonusLabel</td><td>$defenseBonus</td></tr>" +
            "</table>",

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
        UnitCollection units = civilization.getUnits();
        for (AbstractUnit unit : units) {
            int passCost = tile.getPassCost(unit);
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

        return Format.text(
            "<table id='actions_table'>" +
                "<tr><th colspan='2'>$passCostHeader</th></tr>" +
                "$units" +
            "</table>",

            "$passCostHeader", L10nTile.PASS_COST,
            "$units", buf
        );
    }
}
