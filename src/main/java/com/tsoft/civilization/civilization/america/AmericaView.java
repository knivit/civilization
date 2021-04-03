package com.tsoft.civilization.civilization.america;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.CivilizationView;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;

public class AmericaView extends CivilizationView {

    @Override
    public L10n getName() {
        return AMERICA;
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/civilizations/america.png";
    }

}
