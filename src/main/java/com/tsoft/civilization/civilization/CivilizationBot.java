package com.tsoft.civilization.civilization;

import com.tsoft.civilization.world.World;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CivilizationBot implements Runnable {

    protected final World world;
    protected final Civilization civilization;

    @Getter
    public MoveState moveState;

    protected abstract void calculate();

    protected CivilizationBot(World world, Civilization civilization) {
        this.world = world;
        this.civilization = civilization;
    }

    public void startYear() {
        moveState = MoveState.IN_PROGRESS;

        Thread worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
        calculate();

        moveState = MoveState.DONE;

        // If this is a bot (fully managed civilization, not just a human's helper)
        // do a next turn
        if (PlayerType.BOT.equals(civilization.getPlayerType())) {
            civilization.stopYear();
        }
    }
}
