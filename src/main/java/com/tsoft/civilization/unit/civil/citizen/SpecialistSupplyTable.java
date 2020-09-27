package com.tsoft.civilization.unit.civil.citizen;

import com.tsoft.civilization.world.economic.Supply;

import java.util.HashMap;
import java.util.Map;

public final class SpecialistSupplyTable {

    private SpecialistSupplyTable() { }

    private static final Map<SpecialistType, Supply> table = new HashMap<>();

    static {
        table.put(SpecialistType.Artist, Supply.builder().culture(3).greatArtist(3).build());
        table.put(SpecialistType.Musician, Supply.builder().culture(3).greatMusician(3).build());
        table.put(SpecialistType.Writer, Supply.builder().culture(3).greatWriter(3).build());
        table.put(SpecialistType.Engineer, Supply.builder().production(2).greatEngineer(3).build());
        table.put(SpecialistType.Merchant, Supply.builder().gold(2).greatMerchant(3).build());
        table.put(SpecialistType.Scientist, Supply.builder().science(3).greatScientist(3).build());
    }

    public static Supply get(SpecialistType type) {
        return (type == null) ? Supply.EMPTY_SUPPLY : table.get(type);
    }
}
