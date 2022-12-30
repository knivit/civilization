package com.tsoft.civilization.civilization.city.specialist;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.economic.Supply;

import java.util.Objects;

public class CitySpecialistService {

    private final City city;
    private final SpecialistList specialists = new SpecialistList();

    public CitySpecialistService(City city) {
        Objects.requireNonNull(city, "city can't be null");

        this.city = city;
    }

    public int getSpecialistCount() {
        return specialists.size();
    }

    public void addSpecialist(SpecialistType specialistType) {
        Specialist specialist = new Specialist(city, specialistType);
        specialists.add(specialist);
    }

    public void startYear() {
        specialists.forEach(Specialist::startYear);
    }

    public Supply stopYear(Supply supply) {
        return Supply.EMPTY;
    }
}
