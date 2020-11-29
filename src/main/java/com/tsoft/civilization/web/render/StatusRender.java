package com.tsoft.civilization.web.render;

import com.tsoft.civilization.web.render.city.CityStatusRender;
import com.tsoft.civilization.web.render.unit.UnitStatusRender;
import com.tsoft.civilization.world.World;

public class StatusRender {

    private static final MapStatusRender mapStatusRender = new MapStatusRender();
    private static final UnitStatusRender unitStatusRender = new UnitStatusRender();
    private static final CityStatusRender cityStatusRender = new CityStatusRender();

    public void render(StatusContext statusContext, World world) {
        mapStatusRender.render(statusContext, world.getTilesMap());
        unitStatusRender.render(statusContext, world);
        cityStatusRender.render(statusContext, world);
    }
}
