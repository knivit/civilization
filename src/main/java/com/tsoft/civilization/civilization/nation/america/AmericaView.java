package com.tsoft.civilization.civilization.nation.america;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.L10nCivilization;
import lombok.Getter;

public class AmericaView extends CivilizationView {

    @Getter
    public final L10n name = L10nCivilization.AMERICA;

    @Override
    public String getStatusImageSrc() {
        return "images/status/civilizations/america.png";
    }

}
