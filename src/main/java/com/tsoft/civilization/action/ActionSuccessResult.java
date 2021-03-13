package com.tsoft.civilization.action;

import com.tsoft.civilization.L10n.L10n;

public class ActionSuccessResult extends ActionAbstractResult {

    public ActionSuccessResult(L10n message) {
        super(message);
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}
