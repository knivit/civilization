package com.tsoft.civilization.world.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.world.service.CreateWorldParams;
import com.tsoft.civilization.world.service.CreateWorldService;

import java.util.UUID;

public class CreateWorldAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final CreateWorldService createWorldService;

    public CreateWorldAction(CreateWorldService createWorldService) {
        this.createWorldService = createWorldService;
    }

    public ActionAbstractResult create(CreateWorldParams request) {
        return createWorldService.create(request);
    }
}
