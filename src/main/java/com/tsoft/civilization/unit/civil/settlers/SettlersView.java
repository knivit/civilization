package com.tsoft.civilization.unit.civil.settlers;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import com.tsoft.civilization.unit.civil.settlers.action.BuildCityAction;
import com.tsoft.civilization.util.Format;

public class SettlersView extends AbstractUnitView<Settlers> {
    @Override
    public String getLocalizedName() {
        return L10nUnit.SETTLERS_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.SETTLERS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJsonName() {
        return "Settlers";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/settler.png";
    }

    @Override
    public StringBuilder getHtmlActions(Settlers unit) {
        return Format.text(
            "$commonActions" +
            "<tr id='actions_table_row'>$buildCityAction</tr>",

            "$commonActions", super.getHtmlActions(unit),
            "$buildCityAction", BuildCityAction.getHtml(unit)
        );
    }
}
