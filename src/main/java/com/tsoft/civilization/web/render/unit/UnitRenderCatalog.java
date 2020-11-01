package com.tsoft.civilization.web.render.unit;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderCatalog;
import com.tsoft.civilization.web.render.unit.civil.SettlersRender;
import com.tsoft.civilization.web.render.unit.civil.WorkersRender;
import com.tsoft.civilization.web.render.unit.military.ArchersRender;
import com.tsoft.civilization.web.render.unit.military.WarriorsRender;

import java.util.HashMap;
import java.util.Map;

public class UnitRenderCatalog extends RenderCatalog {

    private static final Map<Class<? extends AbstractUnit>, Render<? extends AbstractUnit>> UNIT_RENDERS = new HashMap<>();

    static {
        UNIT_RENDERS.put(Settlers.class, new SettlersRender());
        UNIT_RENDERS.put(Workers.class, new WorkersRender());
        UNIT_RENDERS.put(Archers.class, new ArchersRender());
        UNIT_RENDERS.put(Warriors.class, new WarriorsRender());
    }

    public UnitRenderCatalog() {
        super(UNIT_RENDERS);
    }
}
