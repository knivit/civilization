package com.tsoft.civilization.helper.html;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class HtmlAttribute {

    private final String name;
    private final String value;
}
