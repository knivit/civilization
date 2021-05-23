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
public class HtmlDocument {

    private final List<HtmlTag> tags;

    public HtmlTag findById(String id) {
        if (tags == null) {
            return null;
        }

        return tags.stream()
            .filter(e -> e.attributeExists("id", id))
            .findAny()
            .orElse(null);
    }
}
