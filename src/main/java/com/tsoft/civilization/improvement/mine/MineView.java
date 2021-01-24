package com.tsoft.civilization.improvement.mine;

import com.tsoft.civilization.L10n.L10nImprovement;
import com.tsoft.civilization.improvement.AbstractImprovementView;

public class MineView extends AbstractImprovementView {
    @Override
    public String getLocalizedName() {
        return L10nImprovement.MINE_NAME.getLocalized();
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
