package com.tsoft.civilization.civilization.building.catalog.nationalwonder.oxforduniversity;

import com.tsoft.civilization.civilization.building.AbstractNationalWonderView;
import com.tsoft.civilization.util.l10n.L10n;
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
