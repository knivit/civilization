package com.tsoft.civilization.world;

import java.util.HashMap;
import java.util.Map;

public class EventsByYearMap {
    private final World world;

    private final Map<Year, EventList> history = new HashMap<>();
    private final EventList events = new EventList();

    public EventsByYearMap(World world) {
        this.world = world;
    }

    public synchronized void add(Event event) {
        EventList eventList = history.computeIfAbsent(world.getYear(), s -> new EventList());
        eventList.add(event);

        // Add to the List for client's notifications
        events.add(event);
    }

    public synchronized int size() {
        return events.size();
    }

    public synchronized Event get(int index) {
        return events.get(index);
    }

    public synchronized EventList getEventsForYear(Year year) {
        return history.getOrDefault(year, new EventList());
    }
}
