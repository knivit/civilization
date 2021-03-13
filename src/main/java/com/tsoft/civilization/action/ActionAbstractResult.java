package com.tsoft.civilization.action;

import com.tsoft.civilization.L10n.L10n;

public abstract class ActionAbstractResult {
    private final L10n message;

    public abstract boolean isSuccess();

    public ActionAbstractResult(L10n message) {
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
