package com.tsoft.civilization.action;

import com.tsoft.civilization.L10n.L10nMap;

public abstract class ActionAbstractResult {
    private L10nMap message;

    public abstract boolean isSuccess();

    public ActionAbstractResult(L10nMap message) {
        this.message = message;
    }

    public String getLocalized() {
        return message.getLocalized();
    }

    public boolean isFail() {
        return !isSuccess();
    }

    @Override
    public String toString() {
        return message.getEnglish();
    }
}
