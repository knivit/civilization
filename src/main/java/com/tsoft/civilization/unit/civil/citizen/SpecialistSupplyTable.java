package com.tsoft.civilization.unit.civil.citizen;

import com.tsoft.civilization.economic.Supply;

import java.util.HashMap;
import java.util.Map;

public final class SpecialistSupplyTable {

    private SpecialistSupplyTable() { }

    private static final Map<SpecialistType, Supply> table = new HashMap<>();

    static {
        table.put(SpecialistType.ARTIST, Supply.builder().culture(3).build());//.greatArtist(3).build());
        table.put(SpecialistType.MUSICIAN, Supply.builder().culture(3).build());//.greatMusician(3).build());
        table.put(SpecialistType.WRITER, Supply.builder().culture(3).build());//.greatWriter(3).build());
        table.put(SpecialistType.ENGINEER, Supply.builder().production(2).build());//.greatEngineer(3).build());
        table.put(SpecialistType.MERCHANT, Supply.builder().gold(2).build());//.greatMerchant(3).build());
        table.put(SpecialistType.SCIENTIST, Supply.builder().science(3).build());//.greatScientist(3).build());
    }

    public static Supply get(SpecialistType type) {
        return (type == null) ? Supply.EMPTY : table.get(type);
    }
}
