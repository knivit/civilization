package com.tsoft.civilization.building.nationalwonder.circusmaximus;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractNationalWonderView;
import lombok.Getter;

public class CircusMaximusView extends AbstractNationalWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Circus Maximus")
        .put("ru", "Circus Maximus");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Circus Maximus")
        .put("ru", "Circus Maximus");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/national_wonders/circusmaximus.png";
}
