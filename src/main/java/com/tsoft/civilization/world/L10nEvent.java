package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10n;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.RU;

public class L10nEvent {
    public static final L10n EVENT_TABLE_HEADER = new L10n()
        .put(EN, "Events for a Year")
        .put(RU, "События за год");

    public static final L10n PRIOR_YEAR_BUTTON = new L10n()
        .put(EN, "<<")
        .put(RU, "<<");

    public static final L10n NEXT_YEAR_BUTTON = new L10n()
        .put(EN, ">>")
        .put(RU, ">>");
}
