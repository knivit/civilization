package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class JoinWorldActionResults {
    public static final ActionSuccessResult JOINED = new ActionSuccessResult(L10nWorld.CIVILIZATION_JOINED);

    public static final ActionFailureResult CANT_CREATE_CIVILIZATION = new ActionFailureResult(L10nServer.CANT_CREATE_CIVILIZATION);
    public static final ActionFailureResult WORLD_NOT_FOUND = new ActionFailureResult(L10nServer.WORLD_NOT_FOUND);
    public static final ActionFailureResult WORLD_IS_FULL = new ActionFailureResult(L10nServer.WORLD_IS_FULL);
    public static final ActionFailureResult NO_CIVILIZATION_AVAILABLE = new ActionFailureResult(L10nServer.NO_CIVILIZATION_AVAILABLE);
}
