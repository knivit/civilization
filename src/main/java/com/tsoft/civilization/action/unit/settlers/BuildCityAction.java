package com.tsoft.civilization.action.unit.settlers;

import com.tsoft.civilization.L10n.unit.L10nSettlers;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.Settlers;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Civilization;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
public class BuildCityAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildCity(Settlers settlers) {
        ActionAbstractResult result = canBuildCity(settlers);
        log.debug("{}", result.getLocalized());

        if (result.isFail()) {
            return result;
        }

        City city = new City(settlers.getCivilization(), settlers.getLocation());
        settlers.destroyedBy(null, false);

        return SettlersActionResults.CITY_BUILT;
    }

    private static ActionAbstractResult canBuildCity(Settlers settlers) {
        if (settlers == null || settlers.isDestroyed()) {
            return SettlersActionResults.UNIT_NOT_FOUND;
        }

        // unit must have passing score
        Point location = settlers.getLocation();
        if (settlers.getPassScore() <= 0) {
            return SettlersActionResults.NO_PASS_SCORE;
        }

        // the tile must be not civilized or must be civilized by unit's civilization
        Civilization civilization = settlers.getWorld().getCivilizationOnTile(location);
        if (civilization != null && !civilization.equals(settlers.getCivilization())) {
            return SettlersActionResults.CANT_BUILD_CITY_TILE_IS_OCCUPIED;
        }

        // there should be no cities as far as 4 tiles around
        ArrayList<Point> locations = settlers.getTilesMap().getLocationsAround(location, 4);
        locations.add(location);
        for (Point loc : locations) {
            AbstractTile tile = settlers.getTilesMap().getTile(loc);
            if (tile.getImprovement() != null && City.class.equals(tile.getImprovement().getClass())) {
                return SettlersActionResults.CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY;
            }
        }

        return SettlersActionResults.CAN_BUILD_CITY;
    }

    private static String getClientJSCode(Settlers settlers) {
        return String.format("client.buildCityAction({ settlers:'%1$s' })", settlers.getId());
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

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td><td>$actionDescription</td>",

            "$buttonOnClick", getClientJSCode(settlers),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
