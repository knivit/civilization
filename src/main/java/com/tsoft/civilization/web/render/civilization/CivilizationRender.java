package com.tsoft.civilization.web.render.civilization;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.Civilization;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.civilization.L10nCivilization.*;

public class CivilizationRender {

    private static final Map<L10n, Color> COLORS = new HashMap<>();

    static {
        COLORS.put(RUSSIA, Color.YELLOW);
        COLORS.put(AMERICA, Color.BLUE);
        COLORS.put(JAPAN, Color.RED);
    }

    public Color getColor(Civilization civilization) {
        return COLORS.get(civilization.getName());
    }
}
