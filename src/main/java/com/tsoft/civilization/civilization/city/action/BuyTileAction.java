package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.L10nCity;
import com.tsoft.civilization.civilization.city.tile.BuyTile;
import com.tsoft.civilization.civilization.city.tile.BuyTileService;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;

import java.util.UUID;

public class BuyTileAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final BuyTileService buyTileService = new BuyTileService();

    public ActionAbstractResult buyTile(City city, Point location) {
        return buyTileService.buyTile(city, location);
    }

    private static String getLocalizedName() {
        return L10nCity.BUY_TILE.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nCity.BUY_TILE_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(City city) {
        if (buyTileService.canBuyTile(city).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", getClientJSCode(city),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }

    private StringBuilder getClientJSCode(City city) {
        JsonBlock locations = new JsonBlock('\'');

        locations.startArray("locations");
        for (BuyTile tile : buyTileService.getLocationsToBuy(city)) {
            JsonBlock location = new JsonBlock('\'');
            location.addParam("col", tile.getLocation().getX());
            location.addParam("row", tile.getLocation().getY());
            location.addParam("price", tile.getPrice());
            locations.addElement(location.getText());
        }
        locations.stopArray();

        return ClientAjaxRequest.buyTileAction(city, locations);
    }
}
