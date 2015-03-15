package com.tsoft.civilization.world.economic;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.util.EventList;

public class CivilizationScore {
    private Civilization civilization;
    private CityScore cityScore;

    private CivilizationSupply civilizationTotalSupply = new CivilizationSupply(0, 0, 0, 0);
    private EventList events = new EventList();

    public CivilizationScore(Civilization civilization) {
        this.civilization = civilization;
        cityScore = new CityScore(civilization);
    }

    public void add(CityScore score) {
        cityScore.add(score);
    }

    public void add(CivilizationSupply supply, L10nMap description) {
        civilizationTotalSupply.add(supply);

        Event event = new Event(supply, description, Event.INFORMATION);
        events.add(event);
        civilization.addEvent(event);
    }

    public int getFood() {
        return cityScore.getFood() + civilizationTotalSupply.getFood();
    }

    public int getProduction() {
        return cityScore.getProduction() + civilizationTotalSupply.getProduction();
    }

    public int getGold() {
        return cityScore.getGold() + civilizationTotalSupply.getGold();
    }

    public int getHappiness() {
        return cityScore.getHappiness() + civilizationTotalSupply.getHappiness();
    }

    public int getScience() {
        return cityScore.getScience();
    }

    public int getCulture() {
        return cityScore.getCulture();
    }

    public int getPopulation() {
        return cityScore.getPopulation();
    }
}
