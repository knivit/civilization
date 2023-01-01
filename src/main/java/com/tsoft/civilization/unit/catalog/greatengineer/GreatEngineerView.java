package com.tsoft.civilization.unit.catalog.greatengineer;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import lombok.Getter;

public class GreatEngineerView extends AbstractUnitView {

    @Getter
    public final L10n name = L10nUnit.GREAT_ENGINEER_NAME;

    @Override
    public String getLocalizedName() {
        return name.getLocalized();
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
}
