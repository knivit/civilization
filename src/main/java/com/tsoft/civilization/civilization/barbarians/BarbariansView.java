package com.tsoft.civilization.civilization.barbarians;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.CivilizationView;

import static com.tsoft.civilization.civilization.L10nCivilization.BARBARIANS;

public class BarbariansView extends CivilizationView {

    @Override
    public L10n getName() {
        return BARBARIANS;
    }

    @Override
    public String getStatusImageSrc() {
        return null;
    }

}
