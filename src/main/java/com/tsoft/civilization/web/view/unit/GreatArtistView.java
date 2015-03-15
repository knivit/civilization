package com.tsoft.civilization.web.view.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.GreatArtist;

public class GreatArtistView extends AbstractUnitView<GreatArtist> {
    @Override
    public String getLocalizedName() {
        return L10nUnit.GREAT_ARTIST_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nUnit.GREAT_ARTIST_DESCRIPTION.getLocalized();
    }

    @Override
    public String getJSONName() {
        return "GreatArtist";
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/units/great_artist.png";
    }
}
