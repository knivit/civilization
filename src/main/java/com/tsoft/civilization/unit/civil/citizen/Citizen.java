package com.tsoft.civilization.unit.civil.citizen;

import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.economic.Supply;

import java.util.Objects;
import java.util.UUID;

/**
 * Population is a term used in Civilization V to represent the Citizens living in each of the population centers (cities) of the civilization.
 * Citizens are the main laborers of the empire, working the tiles of terrain around the city to obtain yields, as well as the specialist slots.
 * As such, they're paramount for the productivity of each city, and the empire as a whole.
 *
 * Population is represented by a number that indicates both the number of Citizens inhabiting a city and its general size.
 * This number is not literal, and is instead used for gameplay purposes (for example, an individual Citizen can occupy a single job slot).
 * If a city has 1 Population, or a single Citizen, it does not mean there is literally only one person living there;
 * it means the city is a very small village.
 * A city with a Population of 10 is a concentrated urban area, and a city with a Population of 20 or more is a major metropolitan area.
 * The size differences between cities are represented graphically as well – whereas a newly founded village will barely occupy the middle of its tile,
 * a sprawling metropolis will appear to spill over into neighboring tiles.
 *
 * Each new city starts with a Population of 1, or 1 Citizen.
 * This means that it can work one nearby tile (in addition to the tile the city is placed upon, which is worked for free).
 * It can subsequently grow or shrink its Population according to the rules of Food consumption and distribution,
 * though it will always have at least 1 Citizen.
 *
 * Apart from producing tile yields, citizens also contribute to Science production at
 * the rate of 1 Science per Citizen without additional buildings.
 *
 * Each unit of 1 Population produces 1 Unhappiness, and so happiness serves to cap the number of citizens in the empire.
 */
public class Citizen {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final String id = UUID.randomUUID().toString();

    private final City city;
    private Point location;
    private SpecialistType specialistType;

    private final TileService tileService = new TileService();

    public Citizen(City city) {
        this.city = city;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isUnemployed() {
        return location == null;
    }

    public boolean isSpecialist() {
        return specialistType != null;
    }

    public Supply getSupply() {
        // +1 Production
        // In a few ways, Unemployed citizens behave as specialists.
        // They do not work a tile, and receive the bonus from the Statue of Liberty, as though they were specialists,
        // but receive no other specialist bonuses[Verify. True for Secularism.] and produce little benefit.
        // The number of slots for Unemployed citizens is unlimited.
        if (isUnemployed()) {
            return Supply.builder().production(1).build();
        }

        if (isSpecialist()) {
            return SpecialistSupplyTable.get(specialistType);
        }

        return getCitizenSupply()
            .add(getTileSupply())
            .add(getImprovementsSupply());
    }

    // Base supply from an employed citizen
    private Supply getCitizenSupply() {
        return Supply.builder().science(1).unhappiness(1).build();
    }

    // Get supply from tiles without improvements where the citizen is working
    private Supply getTileSupply() {
        if (location == null) {
            return Supply.EMPTY_SUPPLY;
        }

        AbstractTile tile = city.getTilesMap().getTile(location);
        AbstractImprovement improvement = tile.getImprovement();
        if (improvement == null || !improvement.isBlockingTileSupply()) {
            return tileService.getSupply(tile);
        }

        return Supply.EMPTY_SUPPLY;
    }

    // Get supply from improvements where citizen is working
    private Supply getImprovementsSupply() {
        if (location == null) {
            return Supply.EMPTY_SUPPLY;
        }

        AbstractTile tile = city.getTilesMap().getTile(location);
        AbstractImprovement improvement = tile.getImprovement();
        if (improvement != null && !(improvement instanceof City)) {
            return improvement.getSupply();
        }

        return Supply.EMPTY_SUPPLY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citizen citizen = (Citizen) o;
        return Objects.equals(id, citizen.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
