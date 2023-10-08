package com.unciv.app.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.tsoft.civilization.Civ5;

public class DesktopGame extends Civ5 {

    private Lwjgl3ApplicationConfiguration config;

    private HardenGdxAudio audio = new HardenGdxAudio();
    private DiscordUpdater discordUpdater = new DiscordUpdater();
    private MultiplayerTurnNotifierDesktop turnNotifier = new MultiplayerTurnNotifierDesktop();

    public DesktopGame(Lwjgl3ApplicationConfiguration config) {
        this.config = config;

        config.setWindowListener(turnNotifier);

        /*discordUpdater.setOnUpdate {

            if (!isInitialized)
                return@setOnUpdate null

            DiscordGameInfo info = new DiscordGameInfo();
            val game = gameInfo;

            if (game != null) {
                info.gameTurn = game.turns;
                info.gameLeader = game.getCurrentPlayerCivilization().nation.leaderName;
                info.gameNation = game.getCurrentPlayerCivilization().nation.name;
            }

            return @setOnUpdate info

        }

        discordUpdater.startUpdates();*/
    }

    @Override
    public void installAudioHooks() {
        /*audio.installHooks(
                musicController.getAudioLoopCallback(),
                musicController.getAudioExceptionHandler()
        );*/
    }

    @Override
    public void notifyTurnStarted() {
        //turnNotifier.turnStarted();
    }

    @Override
    public void dispose() {
        //discordUpdater.stopUpdates();
        super.dispose();
    }
}
