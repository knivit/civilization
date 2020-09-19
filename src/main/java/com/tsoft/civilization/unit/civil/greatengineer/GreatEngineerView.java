package com.tsoft.civilization.unit.civil.greatengineer;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import com.tsoft.civilization.unit.civil.greatengineer.action.HurryProductionAction;
import com.tsoft.civilization.unit.civil.greatengineer.action.ManufactureImprovementAction;
import com.tsoft.civilization.util.Format;

public class GreatEngineerView extends AbstractUnitView<GreatEngineer> {
    @Override
    public String getLocalizedName() {
        return L10nUnit.GREAT_ENGINEER_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.GREAT_ENGINEER_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "GreatEngineer";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/great_engineer.png";
    }

    @Override
    public StringBuilder getHtmlActions(GreatEngineer unit) {
        return Format.text(
            "$commonActions" +
            "<tr id='actions_table_row'>$manufactureImprovementAction</tr>" +
            "<tr id='actions_table_row'>$hurryProductionAction</tr>",

            "$commonActions", super.getHtmlActions(unit),
            "$manufactureImprovementAction", ManufactureImprovementAction.getHtml(unit),
            "$hurryProductionAction", HurryProductionAction.getHtml(unit)
        );
    }
}
