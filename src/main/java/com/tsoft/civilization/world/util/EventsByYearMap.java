package com.tsoft.civilization.world.util;

import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.world.World;

import java.util.HashMap;
import java.util.Map;

public class EventsByYearMap {
    private World world;

    private Map<String, EventList> eventsByYearMap = new HashMap<>();
    private EventList events = new EventList();

    public EventsByYearMap(World world) {
        this.world = world;
    }

    public void add(Event event) {
        // Add to the Map
        String key = Integer.toString(world.getYear().getValue());
        EventList eventList = eventsByYearMap.get(key);
        if (eventList == null) {
            eventList = new EventList();
            eventsByYearMap.put(key, eventList);
        }
        eventList.add(event);

        // Add to the List
        events.add(event);
    }

    public int size() {
        return events.size();
    }

    public Event get(int index) {
        return events.get(index);
    }

    public EventList getEventsForYear(Year year) {
        String key = Integer.toString(year.getValue());
        return eventsByYearMap.get(key);
    }
}
