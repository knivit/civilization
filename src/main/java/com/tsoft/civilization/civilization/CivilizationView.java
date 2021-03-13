package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.common.AbstractView;
import com.tsoft.civilization.web.view.JsonBlock;

public class CivilizationView implements AbstractView {
    private L10n name;

    public CivilizationView(L10n name) {
        this.name = name;
    }

    public L10n getName() {
        return name;
    }

    public String getLocalizedName() {
        return L10nCivilization.CIVILIZATION_NAME.getLocalized();
    }

    public String getLocalizedDescription() {
        return L10nCivilization.CIVILIZATION_NAME.getLocalized();
    }

    public String getLocalizedCivilizationName() {
        return name.getLocalized();
    }

    public String getJsonName() {
        return name.getEnglish();
    }

    public JsonBlock getJson(Civilization civilization) {
        JsonBlock civBlock = new JsonBlock();
        civBlock.addParam("name", civilization.getView().getJsonName());

        return civBlock;
    }

    public String getStatusImageSrc() {
        if (name.isEnglishEquals(L10nCivilization.RUSSIA)) return "images/status/civilizations/russia.png";
        if (name.isEnglishEquals(L10nCivilization.AMERICA)) return "images/status/civilizations/america.png";
        return "";
    }
}
