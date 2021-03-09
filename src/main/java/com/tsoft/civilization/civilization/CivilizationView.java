package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.web.view.JsonBlock;

public class CivilizationView {
    private L10nMap name;

    public CivilizationView(L10nMap name) {
        this.name = name;
    }

    public L10nMap getName() {
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
