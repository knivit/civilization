package com.tsoft.civilization.unit.civil.workers;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import com.tsoft.civilization.unit.civil.workers.action.RemoveForestAction;
import com.tsoft.civilization.unit.civil.workers.action.RemoveHillAction;
import com.tsoft.civilization.util.Format;

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
            "$removeForestAction", RemoveForestAction.getHtml(unit),
            "$removeHillAction", RemoveHillAction.getHtml(unit)
        );
    }
}