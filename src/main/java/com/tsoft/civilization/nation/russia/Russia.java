package com.tsoft.civilization.nation.russia;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.world.World;

public class Russia extends Civilization {

    public Russia(World world, PlayerType playerType) {
        super(world, playerType);
    }
    @Override
    protected CivilizationView createView() {
        return new RussiaView();
    }
}

