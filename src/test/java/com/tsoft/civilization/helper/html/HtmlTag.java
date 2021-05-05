package com.tsoft.civilization.helper.html;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HtmlTag {

    private final String name;
    private final List<HtmlAttribute> attributes;
    private final List<HtmlTag> tags;
    private final String text;
}
