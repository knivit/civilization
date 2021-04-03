package com.tsoft.civilization.civilization.russia;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.CivilizationView;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;

public class RussiaView extends CivilizationView {

    @Override
    public L10n getName() {
        return RUSSIA;
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/civilizations/russia.png";
    }
}
