package com.tsoft.civilization.nation.barbarians;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.bot.CivilizationBot;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.world.World;

public class Barbarians extends Civilization {

    public Barbarians(World world, PlayerType playerType) {
        super(world, playerType);
    }

    @Override
    protected CivilizationView createView() {
        return new BarbariansView();
    }

    @Override
    protected CivilizationBot createBot(World world, Civilization civilization) {
        return new BarbariansBot(world, civilization);
    }
}
