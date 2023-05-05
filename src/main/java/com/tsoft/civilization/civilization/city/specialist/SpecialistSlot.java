package com.tsoft.civilization.civilization.city.specialist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SpecialistSlot {

    private final SpecialistType specialistType;

    private int available;
    private int used;
}
