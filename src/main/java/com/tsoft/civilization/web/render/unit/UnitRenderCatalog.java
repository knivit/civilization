package com.tsoft.civilization.web.render.unit;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.catalog.settlers.Settlers;
import com.tsoft.civilization.unit.catalog.workers.Workers;
import com.tsoft.civilization.unit.catalog.archers.Archers;
import com.tsoft.civilization.unit.catalog.warriors.Warriors;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderCatalog;
import com.tsoft.civilization.web.render.unit.civil.SettlersRender;
import com.tsoft.civilization.web.render.unit.civil.WorkersRender;
import com.tsoft.civilization.web.render.unit.military.ArchersRender;
import com.tsoft.civilization.web.render.unit.military.WarriorsRender;

import java.util.HashMap;
import java.util.Map;

public class UnitRenderCatalog extends RenderCatalog {

    private static final Map<String, String> UNIT_RENDERS = new HashMap<>();

    static {
        UNIT_RENDERS.put("Settler", "assets/Images.AbsoluteUnits/TileSets/AbsoluteUnits/Units/Settler.png");
        UNIT_RENDERS.put("Worker", "assets/Images.AbsoluteUnits/TileSets/AbsoluteUnits/Units/Worker.png");
        UNIT_RENDERS.put("Archer", "assets/Images.AbsoluteUnits/TileSets/AbsoluteUnits/Units/Archer.png");
        UNIT_RENDERS.put("Warrior", "assets/Images.AbsoluteUnits/TileSets/AbsoluteUnits/Units/Warrior.png");
    }

    public UnitRenderCatalog() {
        super(UNIT_RENDERS);
    }
}
