package com.tsoft.civilization.bot;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CivilizationBot implements Runnable {

    protected final World world;
    protected final Civilization civilization;

    protected abstract void calculate();

    private Thread worker;

    protected CivilizationBot(World world, Civilization civilization) {
        this.world = world;
        this.civilization = civilization;
    }

    public void startYear() {
        worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
        calculate();

        // If this is a bot (fully managed civilization, not just a human's helper)
        // do a next turn
        if (PlayerType.BOT.equals(civilization.getPlayerType())) {
            civilization.stopYear();
        }
    }

    // Use this method to wait till the helper bot is done
    public void join() {
        if (!PlayerType.BOT.equals(civilization.getPlayerType())) {
            try {
                worker.join();
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
    }
}
