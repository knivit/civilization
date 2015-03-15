package com.tsoft.civilization.world.economic;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.util.EventList;

public class CitizenScore {
    private Civilization civilization;
    private TileScore tileScore;
    private ImprovementScore improvementScore;

    private CitizenSupply citizenTotalSupply = new CitizenSupply(0);
    private EventList events = new EventList();

    public CitizenScore(Civilization civilization) {
        this.civilization = civilization;
        tileScore = new TileScore(civilization);
        improvementScore = new ImprovementScore(civilization);
    }

    public void add(CitizenScore citizenScore) {
        tileScore.add(citizenScore.tileScore);
        improvementScore.add(citizenScore.improvementScore);

        citizenTotalSupply.add(citizenScore.citizenTotalSupply);
        events.addAll(citizenScore.events);
    }

    public void add(CitizenSupply citizenSupply, L10nMap description) {
        citizenTotalSupply.add(citizenSupply);

        Event event = new Event(citizenSupply, description, Event.INFORMATION);
        events.add(event);
        civilization.addEvent(event);
    }

    public void add(TileSupply tileSupply, L10nMap description) {
        tileScore.add(tileSupply, description);
    }

    public void add(TileScore tileScore) {
        this.tileScore.add(tileScore);
    }

    public void add(ImprovementSupply improvementSupply, L10nMap description) {
        improvementScore.add(improvementSupply, description);
    }

    public void add(ImprovementScore  improvementScore) {
        this.improvementScore.add(improvementScore);
    }

    public int getFood() {
        return tileScore.getFood() + improvementScore.getFood() + citizenTotalSupply.getFood();
    }

    public int getProduction() {
        return tileScore.getProduction() + improvementScore.getProduction();
    }

    public int getGold() {
        return tileScore.getGold() + improvementScore.getGold();
    }

    public int getHappiness() {
        return improvementScore.getHappiness();
    }

    public TileScore getTileScore() {
        return tileScore;
    }
}
