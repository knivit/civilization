package com.tsoft.civilization.nation.japan;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.L10nCivilization;
import lombok.Getter;

public class JapanView extends CivilizationView {

    @Getter
    public final L10n name = L10nCivilization.JAPAN;

    @Override
    public String getStatusImageSrc() {
        return "images/status/civilizations/japan.png";
    }
}
