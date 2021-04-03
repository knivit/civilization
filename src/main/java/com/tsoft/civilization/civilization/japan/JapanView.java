package com.tsoft.civilization.civilization.japan;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.CivilizationView;

import static com.tsoft.civilization.civilization.L10nCivilization.JAPAN;

public class JapanView extends CivilizationView {

    @Override
    public L10n getName() {
        return JAPAN;
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/civilizations/japan.png";
    }
}
