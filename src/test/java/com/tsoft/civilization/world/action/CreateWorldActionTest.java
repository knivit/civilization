package com.tsoft.civilization.world.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.world.service.CreateWorldParams;
import com.tsoft.civilization.world.service.CreateWorldService;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.world.service.CreateWorldService.CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CreateWorldActionTest {

    @Test
    public void create() {
        CreateWorldService createWorldService = mock(CreateWorldService.class);
        when(createWorldService.create(any(CreateWorldParams.class))).thenReturn(CREATED);

        CreateWorldAction action = new CreateWorldAction(createWorldService);

        assertThat(action.create(any(CreateWorldParams.class)))
            .isNotNull()
            .returns(true, ActionAbstractResult::isSuccess);

        verify(createWorldService, atLeastOnce()).create(any());
    }
}
