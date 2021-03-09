package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.unit.AbstractUnit;

public interface AbstractAction<U extends AbstractUnit>  {

    StringBuilder getHtmlActions(U unit);
}
