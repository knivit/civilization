package com.tsoft.civilization.improvement.city.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.tile.BuyTileService;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;

import java.util.UUID;

public class BuyTileAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final BuyTileService buyTileService;

    public BuyTileAction(BuyTileService buyTileService) {
        this.buyTileService = buyTileService;
    }

    public ActionAbstractResult buyTile(City city, Point location, int price) {
        return buyTileService.buyTile(city, location, price);
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
        for (Point loc : buyTileService.getLocationsToBuy(city)) {
            JsonBlock location = new JsonBlock('\'');
            location.addParam("col", loc.getX());
            location.addParam("row", loc.getY());
            locations.addElement(location.getText());
        }
        locations.stopArray();

        return ClientAjaxRequest.buyTileAction(city, locations);
    }
}
