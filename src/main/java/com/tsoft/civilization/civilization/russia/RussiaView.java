package com.tsoft.civilization.civilization.russia;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.L10nCivilization;
import lombok.Getter;

public class RussiaView extends CivilizationView {

    @Getter
    public final L10n name = L10nCivilization.RUSSIA;

    @Override
    public String getStatusImageSrc() {
        return "images/status/civilizations/russia.png";
    }
}
