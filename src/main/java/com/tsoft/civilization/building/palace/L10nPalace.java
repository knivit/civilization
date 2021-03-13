package com.tsoft.civilization.building.palace;

import com.tsoft.civilization.L10n.L10n;

public class L10nPalace {
    public static final L10n NAME = new L10n()
            .put("en", "Palace")
            .put("ru", "Дворец");

    public static final L10n DESCRIPTION = new L10n()
            .put("en", "Indicates this City is the Capital of the empire. Connecting other Cities to the Capital by Road will produce additional Gold.")
            .put("ru", "Указывает, что город является столицей империи. Соединяя другие города со столицей дорогами, увеличивается производство золота.");
}
