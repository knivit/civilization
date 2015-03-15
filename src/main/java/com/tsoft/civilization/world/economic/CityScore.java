package com.tsoft.civilization.world.economic;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.util.EventList;

public class CityScore {
    private Civilization civilization;
    private BuildingScore buildingScore;
    private CitizenScore citizenScore;

    private CitySupply cityTotalSupply = new CitySupply(0, 0, 0, 0);
    private EventList events = new EventList();;

    public CityScore(Civilization civilization) {
        this.civilization = civilization;
        buildingScore = new BuildingScore(civilization);
        citizenScore = new CitizenScore(civilization);
    }

    public void add(CityScore cityScore) {
        citizenScore.add(cityScore.citizenScore);
        buildingScore.add(cityScore.buildingScore);

        cityTotalSupply.add(cityScore.cityTotalSupply);
        events.addAll(cityScore.events);
    }

    public void add(CitySupply citySupply, L10nMap description) {
        cityTotalSupply.add(citySupply);

        Event event = new Event(citySupply, description, Event.INFORMATION);
        events.add(event);
        civilization.addEvent(event);
    }

    public void add(BuildingSupply buildingSupply, L10nMap description) {
        buildingScore.add(buildingSupply, description);
    }

    public void add(BuildingScore buildingScore) {
        this.buildingScore.add(buildingScore);
    }

    public void add(CitizenSupply citizenSupply, L10nMap description) {
        citizenScore.add(citizenSupply, description);
    }

    public void add(CitizenScore citizenScore) {
        this.citizenScore.add(citizenScore);
    }

    public int getFood() {
        return citizenScore.getFood() + buildingScore.getFood() + cityTotalSupply.getFood();
    }

    public int getProduction() {
        return citizenScore.getProduction() + buildingScore.getProduction() + cityTotalSupply.getProduction();
    }

    public int getGold() {
        return citizenScore.getGold() + buildingScore.getGold() + cityTotalSupply.getGold();
    }

    public int getScience() {
        return buildingScore.getScience();
    }

    public int getCulture() {
        return buildingScore.getCulture();
    }

    public int getHappiness() {
        return buildingScore.getHappiness() + citizenScore.getHappiness();
    }

    public int getPopulation() {
        return cityTotalSupply.getPopulation();
    }

    public TileScore getTileScore() {
        return citizenScore.getTileScore();
    }
}
