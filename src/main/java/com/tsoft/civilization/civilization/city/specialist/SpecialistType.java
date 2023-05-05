package com.tsoft.civilization.civilization.city.specialist;

import com.tsoft.civilization.civilization.city.citizen.CitizenView;
import com.tsoft.civilization.civilization.city.specialist.view.*;

/**
 * Specialists are citizens of your cities which, instead of trudging in the fields and mines around the cities,
 * dedicate themselves to more refined work in your special buildings' specialist slots.
 * Despite the fact that in this way less land is being taken advantage of, Specialists are very important for your civilization,
 * because they are the main source of GreatPeople Points (GPPs) and are thus central to your Great People generation!
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
 *
 * Artists, Musicians and Writers (Brave New World)
 *
 * In the Brave New World expansion pack, the Artist has been split into three new kinds of Specialist:
 * Writer, Artist and Musician (see below). All the cultural buildings providing slots for Vanilla/Gods and Kings Artists
 * have been altered - they now provide Great Work slots instead. Similarly, all Wonders that used to provide
 * Great Artist points have now been altered to provide Great Work slots of the three types.
 *
 * Only the three Guilds now provide slots for Specialists, as well as GPPs towards the specific Great Person:
 *
 * Artist (Brave New World)
 * +3 Culture
 * +3 Great Artist points
 * Building 	        Specialist slots
 * Artists' Guild       2
 *
 * Musician
 * +3 Culture
 * +3 Great Musician points
 * Building 	        Specialist slots
 * Musicians' Guild     2
 *
 * Writer
 * +3 Culture
 * +3 Great Writer points
 * Building 	        Specialist slots
 * Writers' Guild 	    2
 *
 * Engineer
 * +2 Production
 * +3 Great Engineer points
 * Building 	Specialist slots
 * Workshop Workshop; Longhouse Longhouse 	Engineer (Civ5).png
 * Windmill Windmill; Coffee House Coffee House GodsKings5 clear.png 	Engineer (Civ5).png
 * Factory Factory 	Engineer (Civ5).pngEngineer (Civ5).png
 *
 * Merchant
 * +2 Gold
 * +3 Great Merchant points
 * Building 	                    Specialist slots
 * Market; Bazaar 	                1
 * Bank; Satrap's Court; Hanse 	    1
 * Stock Exchange  	                2
 *
 * Scientist
 * +3 Science
 * +3 Great Scientist points
 * Building 	                    Specialist slots
 * University; Wat 	                2
 * Public School                    1
 * Research Lab                     1
 *
 * Unemployed (Civ5).png Unemployed
 *
 * +1 Production
 *
 * In a few ways, Unemployed citizens behave as Specialists. They do not work a tile, and receive the bonus
 * from the Statue of Liberty, as though they were Specialists, but receive no other specialist
 * bonuses[Verify. True for Secularism.] and produce little benefit. The number of slots for U
 * Unemployed citizens is unlimited.
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
