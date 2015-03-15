package com.tsoft.civilization.world.economic;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.util.EventList;

public class BuildingScore {
    private Civilization civilization;
    private BuildingSupply buildingTotalSupply = new BuildingSupply(0, 0, 0, 0, 0, 0);
    private EventList events = new EventList();

    public BuildingScore(Civilization civilization) {
        this.civilization = civilization;
    }

    public void add(BuildingSupply buildingSupply, L10nMap description) {
        buildingTotalSupply.add(buildingSupply);

        if (civilization != null) {
            Event event = new Event(buildingSupply, description, Event.INFORMATION);
            events.add(event);
            civilization.addEvent(event);
        }
    }

    public void add(BuildingScore score) {
        buildingTotalSupply.add(score.buildingTotalSupply);
        events.addAll(score.events);
    }

    public int getFood() {
        return buildingTotalSupply.getFood();
    }

    public int getProduction() {
        return buildingTotalSupply.getProduction();
    }

    public int getGold() {
        return buildingTotalSupply.getGold();
    }

    public int getScience() {
        return buildingTotalSupply.getScience();
    }

    public int getCulture() {
        return buildingTotalSupply.getCulture();
    }

    public int getHappiness() {
        return buildingTotalSupply.getHappiness();
    }
}
