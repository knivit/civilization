package com.tsoft.civilization.civilization.city.tile;

import com.tsoft.civilization.util.Point;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class TileCost {

    private final Point location;
    private final int price;
}
