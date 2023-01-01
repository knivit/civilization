package com.tsoft.civilization.unit.catalog.greatscientist;

import com.tsoft.civilization.action.AbstractAction;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.catalog.greatscientist.action.AcademyImprovementAction;
import com.tsoft.civilization.unit.catalog.greatscientist.action.LearnNewTechnologyAction;
import com.tsoft.civilization.util.Format;

public class GreatScientistActions implements AbstractAction<GreatScientist> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(GreatScientist unit) {
        return Format.text("""
            $defaultActions
            <tr id='actions_table_row'>$academyImprovementAction</tr>
            <tr id='actions_table_row'>$learnNewTechnologyAction</tr>
            """,

            "$defaultActions", defaultActions.getHtmlActions(unit),
            "$academyImprovementAction", AcademyImprovementAction.getHtml(unit),
            "$learnNewTechnologyAction", LearnNewTechnologyAction.getHtml(unit)
        );
    }
}
