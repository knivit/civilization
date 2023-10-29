package com.tsoft.civilization.unit.catalog.greatartist;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnitView;
import lombok.Getter;

public class GreatArtistView{

    @Getter
    public final L10n name = L10nUnit.GREAT_ARTIST_NAME;

    public String getLocalizedName() {
        return name.getLocalized();
    }

    public String getLocalizedDescription() {
        return L10nUnit.GREAT_ARTIST_DESCRIPTION.getLocalized();
    }

    public String getJsonName() {
        return "GreatArtist";
    }

    public String getStatusImageSrc() {
        return "images/status/units/great_artist.png";
    }
}
