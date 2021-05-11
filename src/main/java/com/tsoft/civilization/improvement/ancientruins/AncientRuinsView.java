package com.tsoft.civilization.improvement.ancientruins;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.improvement.AbstractImprovementView;
import com.tsoft.civilization.improvement.L10nImprovement;
import lombok.Getter;

public class AncientRuinsView extends AbstractImprovementView {

    @Getter
    private final L10n name = L10nImprovement.ANCIENT_RUINS;

    @Override
    public String getLocalizedName() {
        return name.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nImprovement.ANCIENT_RUINS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/improvements/ancient_ruins.png";
    }}
