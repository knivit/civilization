package com.tsoft.civilization.helper.html;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HtmlDocument {

    private final List<HtmlTag> tags;
}
