package com.tsoft.civilization.helper.html;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HtmlAttribute {

    private final String name;
    private final String value;
}
