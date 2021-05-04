package com.tsoft.civilization.civilization;

import com.tsoft.civilization.world.AbstractView;
import com.tsoft.civilization.web.view.JsonBlock;

public abstract class CivilizationView implements AbstractView {

    @Override
    public String getLocalizedName() {
        return L10nCivilization.CIVILIZATION_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nCivilization.CIVILIZATION_NAME.getLocalized();
    }

    public String getLocalizedCivilizationName() {
        return getName().getLocalized();
    }

    public String getJsonName() {
        return getName().getEnglish();
    }

    public JsonBlock getJson(Civilization civilization) {
        JsonBlock civBlock = new JsonBlock();
        civBlock.addParam("name", civilization.getView().getJsonName());

        return civBlock;
    }
}
