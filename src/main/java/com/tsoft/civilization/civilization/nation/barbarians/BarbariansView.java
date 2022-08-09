package com.tsoft.civilization.civilization.nation.barbarians;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.L10nCivilization;
import lombok.Getter;

public class BarbariansView extends CivilizationView {

    @Getter
    public final L10n name = L10nCivilization.BARBARIANS;

    @Override
    public String getStatusImageSrc() {
        return null;
    }

}
