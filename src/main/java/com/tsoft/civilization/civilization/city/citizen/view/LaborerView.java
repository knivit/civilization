package com.tsoft.civilization.civilization.city.citizen.view;

import com.tsoft.civilization.civilization.city.citizen.L10nCitizen;
import com.tsoft.civilization.civilization.city.citizen.CitizenView;

public class LaborerView extends CitizenView {

    @Override
    public String getLocalizedName() {
        return L10nCitizen.LABORER.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return null;
    }

    @Override
    public String getJsonName() {
        return null;
    }

    @Override
    public String getStatusImageSrc() {
        return null;
    }
}
