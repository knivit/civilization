package com.tsoft.civilization.unit.catalog.settlers.action;

import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.unit.catalog.settlers.L10nSettlers;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.unit.catalog.settlers.Settlers;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.tsoft.civilization.unit.action.DefaultUnitActionsResults.*;

public class BuildCityAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CITY_BUILT = new ActionSuccessResult(L10nSettlers.CITY_BUILT);
    public static final ActionSuccessResult CAN_BUILD_CITY = new ActionSuccessResult(L10nSettlers.CAN_BUILD_CITY);

    public static final ActionFailureResult CANT_BUILD_CITY = new ActionFailureResult(L10nSettlers.CANT_BUILD_CITY_TILE_IS_OCCUPIED);
    public static final ActionFailureResult CANT_BUILD_CITY_TILE_IS_OCCUPIED = new ActionFailureResult(L10nSettlers.CANT_BUILD_CITY_TILE_IS_OCCUPIED);
    public static final ActionFailureResult CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY = new ActionFailureResult(L10nSettlers.CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY);

    public static ActionAbstractResult buildCity(Settlers settlers) {
        ActionAbstractResult result = canBuildCity(settlers);

        if (result.isFail()) {
            return result;
        }

        Civilization civilization = settlers.getCivilization();
        City city = civilization.createCity(settlers);
        if (city == null) {
            return CANT_BUILD_CITY;
        }

        return CITY_BUILT;
    }

    private static ActionAbstractResult canBuildCity(Settlers settlers) {
        if (settlers == null || settlers.isDestroyed()) {
            return UNIT_NOT_FOUND;
        }

        // unit must have passing score
        if (settlers.getPassScore() <= 0) {
            return NO_PASS_SCORE;
        }

        // the tile must be not civilized or must be civilized by unit's civilization
        Point location = settlers.getLocation();
        Civilization civilization = settlers.getWorld().getTileService().getCivilizationOnTile(location);
        if (civilization != null && !civilization.equals(settlers.getCivilization())) {
            return CANT_BUILD_CITY_TILE_IS_OCCUPIED;
        }

        // there should be no cities as far as 4 tiles around
        Set<Point> locations = settlers.getTilesMap().getLocationsAround(location, 4);
        locations.add(location);
        for (Point loc : locations) {
            AbstractTerrain tile = settlers.getTilesMap().getTile(loc);
            if (tile.getCity() != null) {
                return CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY;
            }
        }

        return CAN_BUILD_CITY;
    }

    private static String getLocalizedName() {
        return L10nSettlers.BUILD_CITY_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nSettlers.BUILD_CITY_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(Settlers settlers) {
        if (canBuildCity(settlers).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td>
            <td>$actionDescription</td>
            """,

            "$buttonOnClick", ClientAjaxRequest.buildCityAction(settlers),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
