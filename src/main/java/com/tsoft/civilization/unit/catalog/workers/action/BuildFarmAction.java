package com.tsoft.civilization.unit.catalog.workers.action;

import com.tsoft.civilization.improvement.L10nImprovement;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.catalog.farm.farm.Farm;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.unit.catalog.workers.Workers;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;

import java.util.UUID;

import static com.tsoft.civilization.unit.action.DefaultUnitActionsResults.*;

public class BuildFarmAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildFarm(Workers workers) {
        ActionAbstractResult result = canBuildFarm(workers);
        if (result.isFail()) {
            return result;
        }

        return WorkersActionResults.BUILDING_IMPROVEMENT;
    }

    private static ActionAbstractResult canBuildFarm(Workers workers) {
        if (workers == null || workers.isDestroyed()) {
            return UNIT_NOT_FOUND;
        }

        if (!workers.isOnMyTerritory()) {
            return NOT_MY_TERRITORY;
        }

        AbstractTerrain tile = workers.getTile();
        if (!tile.isIn(Grassland.class, Plains.class)) {
            return WorkersActionResults.FAIL_INAPPROPRIATE_TILE;
        }

        if (workers.getCivilization().getCityService().getCityAtLocation(tile.getLocation()) != null) {
            return WorkersActionResults.FAIL_CANT_BUILD_IN_CITY_CENTER;
        }

        if (tile.getImprovements().has(Farm.CLASS_UUID)) {
            return WorkersActionResults.FAIL_IMPROVEMENT_ALREADY_EXISTS;
        }

        return WorkersActionResults.CAN_BUILD_IMPROVEMENT;
    }

    private static String getLocalizedName() {
        return L10nImprovement.BUILD_FARM_ACTION.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nImprovement.BUILD_FARM_ACTION.getLocalized();
    }

    public static StringBuilder getHtml(Workers workers) {
        if (canBuildFarm(workers).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", ClientAjaxRequest.buildFarmAction(workers),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
