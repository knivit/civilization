package com.tsoft.civilization.web.render.unit;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.web.render.StatusContext;
import com.tsoft.civilization.world.World;

public class UnitStatusRender {

    public void render(StatusContext statusContext, World world) {
        StringBuilder buf = new StringBuilder();
        buf.append("<table id='tiles-table'>\n");

        TilesMap map = world.getTilesMap();
        for (int y = 0; y < map.getHeight(); y ++) {
            buf.append("<tr>");

            for (int x = 0; x < map.getWidth(); x ++) {
                UnitList<?> units = world.getUnitsAtLocation(map.getLocation(x, y));

                for (AbstractUnit unit : units) {
                    buf.append("<td>\n");
                    buf.append(unit.getView().getLocalizedName());
                    buf.append("</td>\n");
                }
            }

            buf.append("</tr>\n");
        }

        buf.append("</table>\n");

        statusContext.setUnits(buf.toString());
    }
}
