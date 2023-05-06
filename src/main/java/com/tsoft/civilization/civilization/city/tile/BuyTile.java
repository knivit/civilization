package com.tsoft.civilization.civilization.city.tile;

import com.tsoft.civilization.util.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BuyTile {

    private final Point location;
    private final int price;
}
