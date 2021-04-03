package com.tsoft.civilization.civilization.russia;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.world.World;

public class Russia extends Civilization {

    private static final CivilizationView VIEW = new RussiaView();

    public Russia(World world, PlayerType playerType) {
        super(world, playerType);
    }

    @Override
    public CivilizationView getView() {
        return VIEW;
    }
}
