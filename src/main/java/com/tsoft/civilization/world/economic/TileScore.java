package com.tsoft.civilization.world.economic;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.util.EventList;

public class TileScore {
    private Civilization civilization;
    private TileSupply tileTotalSupply = new TileSupply(0, 0, 0);
    private EventList events = new EventList();

    public TileScore(Civilization civilization) {
        this.civilization = civilization;
    }

    public void add(TileSupply tileSupply, L10nMap description) {
        tileTotalSupply.add(tileSupply);

        if (civilization != null) {
            Event event = new Event(tileSupply, description, Event.INFORMATION);
            events.add(event);
            civilization.addEvent(event);
        }
    }

    public void add(TileScore score) {
        tileTotalSupply.add(score.tileTotalSupply);
        events.addAll(score.events);
    }

    public int getFood() {
        return tileTotalSupply.getFood();
    }

    public int getProduction() {
        return tileTotalSupply.getProduction();
    }

    public int getGold() {
        return tileTotalSupply.getGold();
    }

    public boolean isPositive() {
        return (tileTotalSupply.getFood() > 0) || (tileTotalSupply.getProduction() > 0) || (tileTotalSupply.getGold() > 0);
    }
}
