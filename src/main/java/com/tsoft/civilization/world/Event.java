package com.tsoft.civilization.world;

import com.tsoft.civilization.util.l10n.L10n;

import java.time.Instant;

public interface Event {

    Instant getServerTime();
    boolean isUpdateWorldMap();
    boolean isUpdateControlPanel();
    boolean isUpdateStatusPanel();

    L10n getMessage();
    Object[] getArgs();
}
