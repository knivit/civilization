package com.tsoft.civilization.civilization.japan;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationView;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.world.World;

public class Japan extends Civilization {

    private static final CivilizationView VIEW = new JapanView();

    public Japan(World world, PlayerType playerType) {
        super(world, playerType);
    }

    @Override
    public CivilizationView getView() {
        return VIEW;
    }
}
