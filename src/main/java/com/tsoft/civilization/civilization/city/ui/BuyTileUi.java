package com.tsoft.civilization.civilization.city.ui;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.L10nCity;
import com.tsoft.civilization.civilization.city.tile.TileCost;
import com.tsoft.civilization.civilization.city.action.BuyTileAction;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class BuyTileUi {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final BuyTileAction buyTileAction;

    private static String getLocalizedName() {
        return L10nCity.BUY_TILE.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nCity.BUY_TILE_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(City city) {
        if (buyTileAction.canBuyTile(city).isFail()) {
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
        for (TileCost tile : buyTileAction.getLocationsToBuy(city)) {
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
