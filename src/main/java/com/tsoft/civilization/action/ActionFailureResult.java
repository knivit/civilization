package com.tsoft.civilization.action;

import com.tsoft.civilization.L10n.L10n;

public class ActionFailureResult extends ActionAbstractResult {
    public ActionFailureResult(L10n message) {
        super(message);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
