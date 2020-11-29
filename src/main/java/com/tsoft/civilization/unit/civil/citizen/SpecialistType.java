package com.tsoft.civilization.unit.civil.citizen;

import com.tsoft.civilization.unit.civil.citizen.view.*;

/**
 * Specialists are citizens of your cities which, instead of trudging in the fields and mines around the cities,
 * dedicate themselves to more refined work in your special buildings' specialist slots.
 * Despite the fact that in this way less land is being taken advantage of, Specialists are very important for your civilization,
 * because they are the main source of GreatPeople5 Great People Points (GPPs) and are thus central to your Great People generation!
 *
 * Specialists are divided into several types, depending on the building they're assigned to.
 * Besides certain types of GPPs, each type will also produce some stat - Production, Gold, Culture or Science.
 * The stat they produce is added to the base production of the city, and is thus subject to enhancement bonuses.
 *
 * Specialists are crucial for your Great Person generation, being the most important contributor to that end.
 * If you want a certain Great Person generated, assign Specialists of the relevant type in your cities.
 * But aside from that, Specialists produce valuable stats which aren't usually available in the land (especially Culture and Science).
 * Use them to enhance these sides of your empire's stat generation, particularly in cities that contain bonus buildings for the relevant stat.
 *
 * Despite that Unhappiness from specialists is shown separate to unhappiness from the rest of the population,
 * specialists produce no more Unhappiness than any other Citizen, and can even produce less with the right enhancers.
 */
public enum SpecialistType {

    ARTIST(new ArtistView()),
    MUSICIAN(new MusicianView()),
    WRITER(new WriterView()),
    ENGINEER(new EngineerView()),
    MERCHANT(new MerchantView()),
    SCIENTIST(new ScientistView());

    private final CitizenView view;

    SpecialistType(CitizenView view) {
        this.view = view;
    }

    public CitizenView getView() {
        return view;
    }
}
