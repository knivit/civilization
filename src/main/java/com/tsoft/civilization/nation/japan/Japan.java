package com.tsoft.civilization.nation.japan;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.bot.CivilizationBot;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.world.World;

public class Japan extends Civilization {

    public Japan(World world, PlayerType playerType) {
        super(world, playerType);
    }

    @Override
    protected CivilizationView createView() {
        return new JapanView();
    }

    @Override
    protected CivilizationBot createBot(World world, Civilization civilization) {
        return new JapanBot(world, civilization);
    }
}
