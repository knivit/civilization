package com.tsoft.civilization.web.render.city;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.render.StatusContext;
import com.tsoft.civilization.web.render.civilization.CivilizationRender;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.economic.Supply;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class CityStatusRender {

    private final CivilizationRender civilizationRender = new CivilizationRender();

    public void render(StatusContext statusContext, World world) {
        StringBuilder buf = new StringBuilder();
        buf.append("<table id='cities-table'>\n");

        // Locations of World's population
        Map<Point, Citizen> citizens = world
            .getCivilizations().stream()
            .flatMap(e -> e.cities().stream()
            .flatMap(f -> f.population().getCitizens().stream()))
            .filter(c -> c.getLocation() != null)
            .collect(Collectors.toMap(c -> c.getLocation(), r -> r));

        // Map of cities' territory
        Map<Point, City> cities = new HashMap<>();
        world
            .getCivilizations().stream()
            .flatMap(e -> e.cities().stream())
            .forEach(c -> c.getLocations().forEach(l -> cities.put(l, c)));

        TilesMap map = world.getTilesMap();
        for (int y = 0; y < map.getHeight(); y ++) {
            buf.append("<tr>\n");

            for (int x = 0; x < map.getWidth(); x ++) {
                Point location = map.getLocation(x, y);
                City city = cities.get(location);
                Citizen citizen = citizens.get(location);

                if (citizen == null && city == null) {
                    buf.append("<td>")
                        .append(" ")
                        .append("</td>");
                } else {
                    Color color = (city == null) ? null : civilizationRender.getColor(city.getCivilization());
                    boolean isCityLocation = (city != null) && city.getLocations().contains(location);

                    buf.append("<td").append(isCityLocation ? " style=\"background: " + String.format("#%06x", color.getRGB() & 0x00FFFFFF) + ";\"" : "").append(">\n");
                    if (citizen != null) {
                        buf.append("<table>");
                        buf.append("<tr>")
                            .append("<th>Citizen</th>")
                            .append("<th>Food</th>")
                            .append("<th>Production</th>")
                            .append("<th>Gold</th>")
                            .append("</tr>");

                        Supply supply = citizen.getSupply();
                        buf.append("<tr>")
                            .append("<td>").append(citizen.getView().getLocalizedName()).append("</td>")
                            .append("<td>").append(supply.getFood()).append("</td>")
                            .append("<td>").append(supply.getProduction()).append("</td>")
                            .append("<td>").append(supply.getGold()).append("</td>")
                            .append("</tr>");

                        buf.append("</table>");
                    } else {
                        buf.append(" ");
                    }
                    buf.append("</td>");
                }
            }

            buf.append("</tr>\n");
        }

        buf.append("</table>\n");

        statusContext.setCities(buf.toString());
    }
}
