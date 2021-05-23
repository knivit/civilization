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
public class HtmlText {

    private final String text;
    private final List<HtmlTag> tags;
}
