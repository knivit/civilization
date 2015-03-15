package com.tsoft.civilization.action.unit.workers;

import com.tsoft.civilization.L10n.unit.L10nWorkers;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.feature.Forest;
import com.tsoft.civilization.unit.Workers;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

public class RemoveForestAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult removeForest(Workers workers) {
        ActionAbstractResult result = canRemoveForest(workers);
        if (result.isFail()) {
            return result;
        }

        AbstractTile tile = workers.getTile();
        Forest forest = (Forest)tile.getFeature(Forest.class);
        forest.addStrength(-1);

        return forest.isRemoved() ? WorkersActionResults.FOREST_IS_REMOVED : WorkersActionResults.REMOVING_FOREST;
    }

    private static ActionAbstractResult canRemoveForest(Workers workers) {
        if (workers == null || workers.isDestroyed()) {
            return WorkersActionResults.UNIT_NOT_FOUND;
        }

        AbstractTile tile = workers.getTile();
        Forest forest = (Forest)tile.getFeature(Forest.class);
        if (forest == null) {
            return WorkersActionResults.FAIL_NO_FOREST_HERE;
        }

        if (!workers.getCivilization().isResearched(Technology.MINING)) {
            return WorkersActionResults.FAIL_NO_MINING_TECHNOLOGY;
        }

        return WorkersActionResults.CAN_REMOVE_FOREST;
    }

    private static String getClientJSCode(Workers workers) {
        return String.format("client.removeForestAction({ workers:'%1$s' })", workers.getId());
    }

    private static String getLocalizedName() {
        return L10nWorkers.REMOVE_FOREST_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nWorkers.REMOVE_FOREST_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(Workers workers) {
        if (canRemoveForest(workers).isFail()) {
            return null;
        }

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td><td>$actionDescription</td>",

            "$buttonOnClick", getClientJSCode(workers),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
