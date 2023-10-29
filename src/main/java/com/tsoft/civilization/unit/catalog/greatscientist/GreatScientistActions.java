package com.tsoft.civilization.unit.catalog.greatscientist;

import com.tsoft.civilization.action.AbstractAction;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.catalog.greatscientist.action.AcademyImprovementAction;
import com.tsoft.civilization.unit.catalog.greatscientist.action.LearnNewTechnologyAction;
import com.tsoft.civilization.util.Format;

public class GreatScientistActions implements AbstractAction<AbstractUnit> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(AbstractUnit unit) {
        return Format.text("""
            <tr id='actions_table_row'>$academyImprovementAction</tr>
            <tr id='actions_table_row'>$learnNewTechnologyAction</tr>
            $defaultActions
            """,

            "$academyImprovementAction", AcademyImprovementAction.getHtml(unit),
            "$learnNewTechnologyAction", LearnNewTechnologyAction.getHtml(unit),
            "$defaultActions", defaultActions.getHtmlActions(unit)
        );
    }
}
