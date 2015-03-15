package com.tsoft.civilization.web.view.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.unit.greatscientist.AcademyImprovementAction;
import com.tsoft.civilization.action.unit.greatscientist.LearnNewTechnologyAction;
import com.tsoft.civilization.unit.GreatScientist;
import com.tsoft.civilization.util.Format;

public class GreatScientistView extends AbstractUnitView<GreatScientist> {
    @Override
    public String getLocalizedName() {
        return L10nUnit.GREAT_SCIENTIST_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.GREAT_SCIENTIST_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "GreatScientist";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/great_scientist.png";
    }

    @Override
    public StringBuilder getHtmlActions(GreatScientist unit) {
        return Format.text(
            "$commonActions" +
            "<tr id='actions_table_row'>$academyImprovementAction</tr>" +
            "<tr id='actions_table_row'>$learnNewTechnologyAction</tr>",

            "$commonActions", super.getHtmlActions(unit),
            "$academyImprovementAction", AcademyImprovementAction.getHtml(unit),
            "$learnNewTechnologyAction", LearnNewTechnologyAction.getHtml(unit)
        );
    }
}