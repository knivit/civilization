package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.world.economic.CitizenScore;
import com.tsoft.civilization.world.economic.CitizenSupply;
import com.tsoft.civilization.world.economic.ImprovementScore;
import com.tsoft.civilization.world.economic.TileScore;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CityCitizenService {
    private City city;

    private List<Citizen> citizens = new ArrayList<Citizen>();
    private CitizenScore citizenScore;

    private CitySupplyStrategy supplyStrategy = CitySupplyStrategy.MAX_FOOD;

    public CityCitizenService(City city) {
        this.city = city;
        citizenScore = new CitizenScore(city.getCivilization());
    }

    public int getCitizenCount() {
        return citizens.size();
    }

    public void addCitizen() {
        Citizen citizen = new Citizen();
        citizen.setLocation(findLocationForCitizen());
        citizens.add(citizen);
    }

    public Collection<Point> getCitizenLocations() {
        ArrayList<Point> locations = new ArrayList<Point>();
        for (Citizen citizen : citizens) {
            if (citizen.getLocation() != null) {
                locations.add(citizen.getLocation());
            }
        }
        return locations;
    }

    private Point findLocationForCitizen() {
        Set<Point> locations = new HashSet<Point>();
        locations.addAll(city.getLocations());
        locations.removeAll(getCitizenLocations());

        Point maxLocation = null;
        for (Point location : locations) {
            if (maxLocation == null) {
                maxLocation = location;
                continue;
            }

            AbstractTile tile = city.getTilesMap().getTile(location);
            TileScore tileScore = tile.getSupply();
            AbstractTile maxTile = city.getTilesMap().getTile(maxLocation);
            TileScore maxTileScore = maxTile.getSupply();

            // in case a tile gives the same amount of a needed supply,
            // check also other supplements and select the best supply
            switch (city.getSupplyStrategy()) {
                case MAX_FOOD: {
                    if (tileScore.getFood() > maxTileScore.getFood()) {
                        maxLocation = location;
                    }
                    if (tileScore.getFood() == maxTileScore.getFood() &&
                        (tileScore.getProduction() > maxTileScore.getProduction() ||
                         (tileScore.getGold() > maxTileScore.getGold()))) {
                        maxLocation = location;
                    }
                    break;
                }
                case MAX_PRODUCTION: {
                    if (tileScore.getProduction() > maxTileScore.getProduction()) {
                        maxLocation = location;
                    }
                    if (tileScore.getProduction() == maxTileScore.getProduction() &&
                        (tileScore.getFood() > maxTileScore.getFood() ||
                         (tileScore.getGold() > maxTileScore.getGold()))) {
                        maxLocation = location;
                    }
                    break;
                }
                case MAX_GOLD: {
                    if (tileScore.getGold() > maxTileScore.getGold()) {
                        maxLocation = location;
                    }
                    if (tileScore.getGold() == maxTileScore.getGold() &&
                        (tileScore.getFood() > maxTileScore.getFood() ||
                         (tileScore.getProduction() > maxTileScore.getProduction()))) {
                        maxLocation = location;
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown supply strategy = " + city.getSupplyStrategy());
                }
            }
        }

        // if the selected tile provides empty (or negative) supply, don't place a citizen here
        AbstractTile maxTile = city.getTilesMap().getTile(maxLocation);
        TileScore maxTileScore = maxTile.getSupply();
        return (maxTileScore.isPositive() ? maxLocation: null);
    }

    public CitySupplyStrategy getSupplyStrategy() {
        return supplyStrategy;
    }

    public void setSupplyStrategy(CitySupplyStrategy supplyStrategy) {
        this.supplyStrategy = supplyStrategy;
        reorganizeCitizensOnTiles();
    }

    // Get supply from tiles without improvements where citizens are working
    private TileScore getTileTotalScore() {
        TileScore score = new TileScore(city.getCivilization());
        for (Citizen citizen : citizens) {
            Point location = citizen.getLocation();

            // unemployed citizen
            if (location == null) {
                continue;
            }

            AbstractTile tile = city.getTilesMap().getTile(location);
            AbstractImprovement improvement = tile.getImprovement();
            if (improvement == null || !improvement.isBlockingTileSupply()) {
                score.add(tile.getSupply());
            }
        }
        return score;
    }

    // Get supply from improvements where citizens are working
    private ImprovementScore getImprovementTotalScore() {
        ImprovementScore score = new ImprovementScore(city.getCivilization());
        for (Citizen citizen : citizens) {
            Point location = citizen.getLocation();

            // unemployed citizen
            if (location == null) {
                continue;
            }

            AbstractTile tile = city.getTilesMap().getTile(location);
            AbstractImprovement improvement = tile.getImprovement();
            if (improvement != null) {
                score.add(improvement.getSupply());
            }
        }
        return score;
    }

    public CitizenScore getCitizenScore() {
        return citizenScore;
    }

    // Citizen's birth, death, happiness
    public void step() {
        citizenScore = new CitizenScore(city.getCivilization());

        // income from tiles and improvements
        citizenScore.add(getTileTotalScore());
        citizenScore.add(getImprovementTotalScore());

        // expenses
        // one Citizen consumes 1 food
        int consumedFood = getCitizenCount();
        CitizenSupply supply = new CitizenSupply(-consumedFood);
        citizenScore.add(supply, L10nCity.CITIZEN_FOOD_EXPENSES);
    }

    private void reorganizeCitizensOnTiles() {
        // first, clear all tiles from citizens
        for (Citizen citizen : citizens) {
            citizen.setLocation(null);
        }

        // second, set them to work
        for (Citizen citizen : citizens) {
            citizen.setLocation(findLocationForCitizen());
        }
    }
}
