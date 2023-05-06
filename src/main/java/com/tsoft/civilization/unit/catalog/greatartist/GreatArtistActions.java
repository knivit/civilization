package com.tsoft.civilization.unit.catalog.greatartist;

import com.tsoft.civilization.action.AbstractAction;
import com.tsoft.civilization.unit.action.DefaultUnitActions;
import com.tsoft.civilization.unit.catalog.greatartist.action.CultureBombAction;
import com.tsoft.civilization.unit.catalog.greatartist.action.LandmarkImprovementAction;
import com.tsoft.civilization.util.Format;

public class GreatArtistActions implements AbstractAction<GreatArtist> {

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    @Override
    public StringBuilder getHtmlActions(GreatArtist unit) {
        return Format.text("""
            $commonActions
            <tr id='actions_table_row'>$cultureBombAction</tr>
            <tr id='actions_table_row'>$landmarkImprovementAction</tr>
            """,

            "$cultureBombAction", CultureBombAction.getHtml(unit),
            "$landmarkImprovementAction", LandmarkImprovementAction.getHtml(unit),
            "$defaultActions", defaultActions.getHtmlActions(unit)
        );
    }
}
