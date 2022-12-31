package com.tsoft.civilization.improvement.mine;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.improvement.AbstractImprovementView;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class MineView extends AbstractImprovementView {

    private static final L10n MINE_NAME = new L10n()
        .put(EN, "Mine")
        .put(RU, "Рудник");

    private static final L10n MINE_DESCRIPTION = new L10n()
        .put(EN, "The Mine grants access to several strategic and luxury resources. It can also be built on hills near cities to improve their Production potential.")
        .put(RU, "Рудник дает доступ к некоторым стратегическим ресурсам и ресурсам роскоши.");

    @Override
    public L10n getName() {
        return MINE_NAME;
    }

    @Override
    public String getLocalizedDescription() {
        return MINE_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/improvements/mine.png";
    }
}
