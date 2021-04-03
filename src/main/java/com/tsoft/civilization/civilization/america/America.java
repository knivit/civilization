package com.tsoft.civilization.civilization.america;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.world.World;

public class America extends Civilization {

    private static final CivilizationView VIEW = new AmericaView();

    public America(World world, PlayerType playerType) {
        super(world, playerType);
    }

    @Override
    public CivilizationView getView() {
        return VIEW;
    }
}
