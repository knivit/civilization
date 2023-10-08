package com.unciv.utils;

public interface PlatformSpecific {

    /** Notifies player that his multiplayer turn started */
    default void notifyTurnStarted() { }

    /** Install system audio hooks */
    default void installAudioHooks() { }

}
