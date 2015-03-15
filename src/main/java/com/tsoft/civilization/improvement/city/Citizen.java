package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.util.Point;

public class Citizen {
    // null, if the citizen is unemployed
    private Point location;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
