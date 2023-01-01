package com.tsoft.civilization.unit.catalog.workers.action;

import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.unit.catalog.workers.L10nWorkers;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.unit.catalog.workers.Workers;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;

import java.util.UUID;

public class RemoveHillAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CAN_REMOVE_HILL = new ActionSuccessResult(L10nWorkers.CAN_REMOVE_HILL);
    public static final ActionSuccessResult HILL_IS_REMOVED = new ActionSuccessResult(L10nWorkers.HILL_IS_REMOVED);
    public static final ActionSuccessResult REMOVING_HILL = new ActionSuccessResult(L10nWorkers.REMOVING_HILL);

    public static final ActionFailureResult FAIL_NO_HILL_HERE = new ActionFailureResult(L10nWorkers.FAIL_NO_HILL_HERE);

    public static ActionAbstractResult removeHill(Workers workers) {
        ActionAbstractResult result = canRemoveHill(workers);
        if (result.isFail()) {
            return result;
        }

        AbstractTerrain tile = workers.getTile();
        Hill hill = tile.getFeature(Hill.class);
        if (hill == null) {
            return FAIL_NO_HILL_HERE;
        }

        hill.addStrength(-1);
        return hill.isRemoved() ? HILL_IS_REMOVED : REMOVING_HILL;
    }

    private static ActionAbstractResult canRemoveHill(Workers workers) {
        if (workers == null || workers.isDestroyed()) {
            return WorkersActionResults.UNIT_NOT_FOUND;
        }

        AbstractTerrain tile = workers.getTile();
        if (!tile.hasFeature(Hill.class)) {
            return FAIL_NO_HILL_HERE;
        }

        if (!workers.getCivilization().isResearched(Technology.MINING)) {
            return WorkersActionResults.FAIL_NO_MINING_TECHNOLOGY;
        }

        // there must be no forest on the hill
        if (tile.getFeatures().size() != 1) {
            return WorkersActionResults.FAIL_FOREST_MUST_BE_REMOVED_FIRST;
        }

        return CAN_REMOVE_HILL;
    }

    private static String getLocalizedName() {
        return L10nWorkers.REMOVE_HILL_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nWorkers.REMOVE_HILL_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(Workers workers) {
        if (canRemoveHill(workers).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", ClientAjaxRequest.removeHillAction(workers),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
