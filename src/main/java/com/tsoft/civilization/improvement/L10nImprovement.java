package com.tsoft.civilization.improvement;

import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class L10nImprovement {

    public static final L10n IMPROVEMENT_NOT_FOUND = new L10n()
        .put("en", "Improvement not found")
        .put("ru", "Улучшение не найдено");

    /** Actions */
    public static final L10n BUILD_FARM_ACTION = new L10n()
        .put(EN, "Build a Farm")
        .put(RU, "Построить ферму");
}
