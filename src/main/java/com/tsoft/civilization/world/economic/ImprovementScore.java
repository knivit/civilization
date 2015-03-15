package com.tsoft.civilization.world.economic;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.util.EventList;

public class ImprovementScore {
    private Civilization civilization;
    private ImprovementSupply improvementTotalSupply = new ImprovementSupply(0, 0, 0, 0);
    private EventList events = new EventList();

    public ImprovementScore(Civilization civilization) {
        this.civilization = civilization;
    }

    public void add(ImprovementSupply improvementSupply, L10nMap description) {
        improvementTotalSupply.add(improvementSupply);

        Event event = new Event(improvementSupply, description, Event.INFORMATION);
        events.add(event);
        civilization.addEvent(event);
    }

    public void add(ImprovementScore score) {
        improvementTotalSupply.add(score.improvementTotalSupply);
        events.addAll(score.events);
    }

    public int getFood() {
        return improvementTotalSupply.getFood();
    }

    public int getProduction() {
        return improvementTotalSupply.getProduction();
    }

    public int getGold() {
        return improvementTotalSupply.getGold();
    }

    public int getHappiness() {
        return improvementTotalSupply.getHappiness();
    }
}
