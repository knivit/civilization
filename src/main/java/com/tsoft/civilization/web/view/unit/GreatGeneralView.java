package com.tsoft.civilization.web.view.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.unit.greatgeneral.CitadelImprovementAction;
import com.tsoft.civilization.action.unit.greatgeneral.CombatBonusAction;
import com.tsoft.civilization.unit.civil.GreatGeneral;
import com.tsoft.civilization.util.Format;

public class GreatGeneralView extends AbstractUnitView<GreatGeneral> {
    @Override
    public String getLocalizedName() {
        return L10nUnit.GREAT_GENERAL_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.GREAT_GENERAL_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "GreatGeneral";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/great_general.png";
    }

    @Override
    public StringBuilder getHtmlActions(GreatGeneral unit) {
        return Format.text(
            "$commonActions" +
            "<tr id='actions_table_row'>$citadelImprovementAction</tr>" +
            "<tr id='actions_table_row'>$combatBonusAction</tr>",

            "$commonActions", super.getHtmlActions(unit),
            "$citadelImprovementAction", CitadelImprovementAction.getHtml(unit),
            "$combatBonusAction", CombatBonusAction.getHtml(unit)
        );
    }
}
