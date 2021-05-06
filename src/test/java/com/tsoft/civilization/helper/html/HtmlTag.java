package com.tsoft.civilization.helper.html;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class HtmlTag {

    private final String name;
    private final List<HtmlAttribute> attributes;
    private final List<HtmlTag> tags;
    private final String text;
}
