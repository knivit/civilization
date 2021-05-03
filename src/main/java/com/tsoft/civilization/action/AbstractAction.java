package com.tsoft.civilization.action;

public interface AbstractAction<T>  {

    StringBuilder getHtmlActions(T object);
}
