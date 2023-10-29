package com.tsoft.civilization.unit.catalog.settlers;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import lombok.Getter;

public class SettlersView {

    @Getter
    public final L10n name = L10nUnit.SETTLERS_NAME;

    public String getLocalizedName() {
        return name.getLocalized();
    }

    public String getLocalizedDescription() {
        return L10nUnit.SETTLERS_DESCRIPTION.getLocalized();
    }

    public String getJsonName() {
        return "Settlers";
    }

    public String getStatusImageSrc() {
        return "images/status/units/settler.png";
    }
}
