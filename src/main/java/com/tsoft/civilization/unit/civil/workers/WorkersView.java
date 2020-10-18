package com.tsoft.civilization.unit.civil.workers;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import com.tsoft.civilization.unit.civil.workers.action.BuildFarmAction;
import com.tsoft.civilization.unit.civil.workers.action.RemoveForestAction;
import com.tsoft.civilization.unit.civil.workers.action.RemoveHillAction;
import com.tsoft.civilization.util.Format;

/**
 * The Worker is vital for a civilization - absolutely the most important civilian unit in the game.
 * It builds all land improvements like Farms and Mines that give access to resources and improve tile productivity.
 * It also constructs roads and railroads to connect the cities, both in an empire and city-states.
 * Finally, it can repair damaged improvements.
 *
 * Workers may also clean tiles from vegetation or marshland, thus freeing resources found on them for exploration.
 *
 * Each of the individual improvement types, as well as the different Remove actions, only become available after you research certain technologies.
 *
 * To build an improvement, the Worker needs to be within a nation's territory, so you must make sure first
 * to expand your borders to the tile where you want to build. Roads and railroads, however, can be built both within
 * and outside of a nation's territory. Likewise, Forts may be built and the Remove Jungle/Forest/Marsh actions
 * may be performed outside of your territory.
 *
 * While Workers cannot be gifted to a city-state, they can perform tasks such as repairing pillaged tiles in one.
 *
 * The Worker is a civilian unit. While it can clear a vacant barbarian encampment, it cannot defend itself, so accompany
 * it with a military unit when it is in dangerous territory. Otherwise you risk him being captured by the enemy!
 *
 * Once a player researches Industrialization, all of his or her Workers change in appearance, looking more like stereotypical factory workers.
 */
public class WorkersView extends AbstractUnitView<Workers> {
    @Override
    public String getLocalizedName() {
        return L10nUnit.WORKERS_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.WORKERS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "Workers";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/workers.png";
    }

    @Override
    public StringBuilder getHtmlActions(Workers unit) {
        return Format.text(
            "$commonActions" +
            "<tr id='actions_table_row'>$removeForestAction</tr>" +
            "<tr id='actions_table_row'>$removeHillAction</tr>",

            "$commonActions", super.getHtmlActions(unit),
            "$buildFarmAction", BuildFarmAction.getHtml(unit),
            "$removeForestAction", RemoveForestAction.getHtml(unit),
            "$removeHillAction", RemoveHillAction.getHtml(unit)
        );
    }
}