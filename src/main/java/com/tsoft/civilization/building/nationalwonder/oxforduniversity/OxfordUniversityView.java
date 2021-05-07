package com.tsoft.civilization.building.nationalwonder.oxforduniversity;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractNationalWonderView;
import lombok.Getter;

public class OxfordUniversityView extends AbstractNationalWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Oxford University")
        .put("ru", "Oxford University");

    private static final L10n DESCRIPTION = new L10n()
        .put("en", "Oxford University")
        .put("ru", "Oxford University");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/national_wonders/oxforduniversity.png";
}
