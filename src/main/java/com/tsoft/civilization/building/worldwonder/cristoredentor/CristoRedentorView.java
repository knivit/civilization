package com.tsoft.civilization.building.worldwonder.cristoredentor;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractWorldWonderView;
import lombok.Getter;

public class CristoRedentorView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Cristo Redentor")
        .put("ru", "Cristo Redentor");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Cristo Redentor")
        .put("ru", "Cristo Redentor");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/cristoredentor.png";
}
