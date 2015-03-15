package com.tsoft.civilization.action;

import com.tsoft.civilization.L10n.L10nMap;

public class ActionSuccessResult extends ActionAbstractResult {
    public ActionSuccessResult(L10nMap message) {
        super(message);
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}
