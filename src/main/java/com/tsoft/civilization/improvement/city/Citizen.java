package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.util.Point;

import java.util.Objects;
import java.util.UUID;

public class Citizen {
    private String id = UUID.randomUUID().toString();

    // null, if the citizen is unemployed
    private Point location;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isUnemplyed() {
        return location == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citizen citizen = (Citizen) o;
        return Objects.equals(id, citizen.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
