package com.tsoft.civilization.action.unit.workers;

import com.tsoft.civilization.L10n.unit.L10nWorkers;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.feature.Hill;
import com.tsoft.civilization.unit.Workers;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

public class RemoveHillAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult removeHill(Workers workers) {
        ActionAbstractResult result = canRemoveHill(workers);
        if (result.isFail()) {
            return result;
        }

        AbstractTile tile = workers.getTile();
        Hill hill = (Hill)tile.getFeature(Hill.class);
        hill.addStrength(-1);

        return hill.isRemoved() ? WorkersActionResults.HILL_IS_REMOVED : WorkersActionResults.REMOVING_HILL;
    }

    private static ActionAbstractResult canRemoveHill(Workers workers) {
        if (workers == null || workers.isDestroyed()) {
            return WorkersActionResults.UNIT_NOT_FOUND;
        }

        AbstractTile tile = workers.getTile();
        Hill hill = (Hill)tile.getFeature(Hill.class);
        if (hill == null) {
            return WorkersActionResults.FAIL_NO_HILL_HERE;
        }

        if (!workers.getCivilization().isResearched(Technology.MINING)) {
            return WorkersActionResults.FAIL_NO_MINING_TECHNOLOGY;
        }

        // there must be no forest on the hill
        if (tile.getFeatures().size() != 1) {
            return WorkersActionResults.FAIL_FOREST_MUST_BE_REMOVED_FIRST;
        }

        return WorkersActionResults.CAN_REMOVE_HILL;
    }

    private static String getClientJSCode(Workers workers) {
        return String.format("client.removeHillAction({ workers:'%1$s' })", workers.getId());
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

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td><td>$actionDescription</td>",

            "$buttonOnClick", getClientJSCode(workers),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
