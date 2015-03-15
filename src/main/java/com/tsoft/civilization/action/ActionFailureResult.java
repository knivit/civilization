package com.tsoft.civilization.action;

import com.tsoft.civilization.L10n.L10nMap;

public class ActionFailureResult extends ActionAbstractResult {
    public ActionFailureResult(L10nMap message) {
        super(message);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
