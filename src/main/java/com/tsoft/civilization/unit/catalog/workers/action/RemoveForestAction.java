package com.tsoft.civilization.unit.catalog.workers.action;

import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.catalog.workers.L10nWorkers;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;

import java.util.UUID;

import static com.tsoft.civilization.unit.action.DefaultUnitActionsResults.*;

public class RemoveForestAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CAN_REMOVE_FOREST = new ActionSuccessResult(L10nWorkers.CAN_REMOVE_FOREST);
    public static final ActionSuccessResult FOREST_IS_REMOVED = new ActionSuccessResult(L10nWorkers.FOREST_IS_REMOVED);
    public static final ActionSuccessResult REMOVING_FOREST = new ActionSuccessResult(L10nWorkers.REMOVING_FOREST);

    public static final ActionFailureResult FAIL_NO_FOREST_HERE = new ActionFailureResult(L10nWorkers.FAIL_NO_FOREST_HERE);

    public static ActionAbstractResult removeForest(AbstractUnit workers) {
        ActionAbstractResult result = canRemoveForest(workers);
        if (result.isFail()) {
            return result;
        }

        AbstractTerrain tile = workers.getTile();
        Forest forest = tile.getFeature(Forest.class);
        if (forest == null) {
            return FAIL_NO_FOREST_HERE;
        }

        forest.addStrength(-1);
        return forest.isRemoved() ? FOREST_IS_REMOVED : REMOVING_FOREST;
    }

    private static ActionAbstractResult canRemoveForest(AbstractUnit workers) {
        if (workers == null || workers.isDestroyed()) {
            return UNIT_NOT_FOUND;
        }

        if (!workers.isOnMyTerritory()) {
            return NOT_MY_TERRITORY;
        }

        AbstractTerrain tile = workers.getTile();
        if (!tile.hasFeature(Forest.class)) {
            return FAIL_NO_FOREST_HERE;
        }

        if (!workers.getCivilization().isResearched(Technology.MINING)) {
            return WorkersActionResults.FAIL_NO_MINING_TECHNOLOGY;
        }

        return CAN_REMOVE_FOREST;
    }

    private static String getLocalizedName() {
        return L10nWorkers.REMOVE_FOREST_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nWorkers.REMOVE_FOREST_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(AbstractUnit workers) {
        if (canRemoveForest(workers).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", ClientAjaxRequest.removeForestAction(workers),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
