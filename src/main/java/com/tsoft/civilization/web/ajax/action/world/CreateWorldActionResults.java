package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class CreateWorldActionResults {
    public static final ActionSuccessResult CREATED = new ActionSuccessResult(L10nWorld.WORLD_CREATED);

    public static final ActionFailureResult CANT_CREATE_CIVILIZATION = new ActionFailureResult(L10nServer.CANT_CREATE_CIVILIZATION);
    public static final ActionFailureResult INVALID_MAP_SIZE = new ActionFailureResult(L10nWorld.INVALID_MAP_SIZE);
    public static final ActionFailureResult INVALID_WORLD_NAME = new ActionFailureResult(L10nWorld.INVALID_WORLD_NAME);
    public static final ActionFailureResult INVALID_CIVILIZATIONS_NUMBER = new ActionFailureResult(L10nWorld.INVALID_CIVILIZATIONS_NUMBER);
}
