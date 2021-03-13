package com.tsoft.civilization.unit.civil.citizen.view;

import com.tsoft.civilization.unit.civil.citizen.L10nCitizen;
import com.tsoft.civilization.unit.civil.citizen.CitizenView;

public class ArtistView extends CitizenView {

    @Override
    public String getLocalizedName() {
        return L10nCitizen.ARTIST.getLocalized();
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
