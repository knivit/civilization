package com.tsoft.civilization.civilization.city.specialist.view;

import com.tsoft.civilization.civilization.city.citizen.CitizenView;
import com.tsoft.civilization.civilization.city.specialist.L10nSpecialist;

public class ArtistView extends CitizenView {

    @Override
    public String getLocalizedName() {
        return L10nSpecialist.ARTIST.getLocalized();
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
