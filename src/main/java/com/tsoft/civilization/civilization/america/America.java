package com.tsoft.civilization.civilization.america;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationBot;
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

    @Override
    protected CivilizationBot createBot(World world, Civilization civilization) {
        return new AmericaBot(world, civilization);
    }
}
