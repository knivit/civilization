package com.tsoft.civilization.improvement.mine;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.improvement.L10nImprovement;
import com.tsoft.civilization.improvement.AbstractImprovementView;
import lombok.Getter;

public class MineView extends AbstractImprovementView {

    @Getter
    private final L10n name = L10nImprovement.MINE_NAME;

    @Override
    public String getLocalizedName() {
        return name.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nImprovement.MINE_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/improvements/mine.png";
    }
}
