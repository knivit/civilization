package com.unciv.ui.screens.basescreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/** Main stage for the game. Catches all exceptions or errors thrown by event handlers,
 *  calling [com.unciv.UncivGame.handleUncaughtThrowable] with the thrown exception or error. */
public class UncivStage extends Stage {

    private final Viewport viewport;

    public UncivStage(Viewport viewport) {
        super(viewport, getBatch(1000));
        this.viewport = viewport;
    }

    public static Batch getBatch(int size) {
        return new SpriteBatch(size);
    }
}
