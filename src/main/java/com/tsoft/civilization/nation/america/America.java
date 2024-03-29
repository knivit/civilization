package com.tsoft.civilization.nation.america;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.world.World;

public class America extends Civilization {

    public America(World world, PlayerType playerType) {
        super(world, playerType);
    }

    @Override
    protected CivilizationView createView() {
        return new AmericaView();
    }
}
