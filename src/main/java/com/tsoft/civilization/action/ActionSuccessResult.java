package com.tsoft.civilization.action;

import com.tsoft.civilization.util.l10n.L10n;

public class ActionSuccessResult extends ActionAbstractResult {

    public ActionSuccessResult(L10n message) {
        super(message);
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}
