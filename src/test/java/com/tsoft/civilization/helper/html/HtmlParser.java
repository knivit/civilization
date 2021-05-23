package com.tsoft.civilization.helper.html;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlParser {

    // <tag/>
    // <tag></tag>
    // <tag attribute="value"/>
    // <tag attribute="value"></tag>
    // <tag attribute="value">text</tag>

    public static HtmlDocument parse(StringBuilder data) {
        return HtmlParser.parse(data.toString());
    }

    public static HtmlDocument parse(byte[] data) {
        return HtmlParser.parse(new String(data, StandardCharsets.UTF_8));
    }

    public static HtmlDocument parse(String data) {
        HtmlInput input = HtmlInput.of(data);
        return HtmlDocument.builder()
            .tags(readTags(input))
            .build();
    }

    private static List<HtmlTag> readTags(HtmlInput input) {
        List<HtmlTag> tags = new ArrayList<>();

        while (true) {
            input.skipBlank();
            if (!input.ended() && !input.startWith("</")) {

                HtmlTag tag = readTag(input);
                tags.add(tag);
            } else {
                break;
            }
        }

        return tags;
    }

    // name='value'
    private static List<HtmlAttribute> readAttributes(HtmlInput input) {
        List<HtmlAttribute> attrs = new ArrayList<>();

        while (true) {
            Character ch = input.peekChar();
            if (!input.ended() && ch != null && ch != '/' && ch != '>') {
                String name = input.readToken();
                String val = null;

                ch = input.peekChar();
                if (ch != null && ch == '=') {
                    input.readChar();
                    val = input.readValue();
                }

                attrs.add(new HtmlAttribute(name, val));
            } else {
                break;
            }
        }
        return attrs;
    }

    static HtmlTag readTag(HtmlInput input) {
        input.skipBlank();

        Character ch = input.readChar();
        if (!input.ended() && ch != null && ch == '<') {
            String name = input.readToken();

            // <tag attrs>
            List<HtmlAttribute> attrs = Collections.emptyList();
            ch = input.peekChar();
            if (!input.ended() && ch != null && ch != '>' && ch != '/') {
                attrs = readAttributes(input);

            }

            // <tag />
            ch = input.peekChar();
            if (!input.ended() && ch != null && ch == '/') {
                input.readChar();

                if (input.readChar() != '>') {
                    throw new IllegalStateException("Char '>' expected at position = " + input.getPos());
                }

                return HtmlTag.builder()
                    .name(name)
                    .attributes(attrs)
                    .tags(Collections.emptyList())
                    .text(null)
                    .build();
            }

            // <tag> text
            if (input.readChar() != '>') {
                throw new IllegalStateException("Char '>' expected at position = " + input.getPos());
            }

            ch = input.peekChar();
            if (!input.ended() && ch == '<') {
                List<HtmlTag> tags = readTags(input);

                if (!input.closeTag(name)) {
                    throw new IllegalStateException("Close tag </" + name + "> expected at position = " + input.getPos());
                }

                return HtmlTag.builder()
                    .name(name)
                    .attributes(attrs)
                    .tags(tags)
                    .text(null)
                    .build();
            }

            HtmlText text = input.readText(HtmlParser::readTag);
            if (input.getLevel() == 0 && !input.closeTag(name)) {
                throw new IllegalStateException("Close tag </" + name + "> expected at position = " + input.getPos());
            }

            return HtmlTag.builder()
                .name(name)
                .attributes(attrs)
                .tags(Collections.emptyList())
                .text(text)
                .build();
        }

        throw new IllegalStateException("Something wrong at position = " + input.getPos());
    }

    @Test
    public void parse_html() {
        HtmlDocument doc = HtmlParser.parse("""
                <td><button onclick='server.click({ "col"=1, "row"=1 })'>Click</button></td>
                <td>test</td>
            """);

        assertThat(doc).isNotNull()
            .returns(2, e -> e.getTags().size())
            .extracting(e -> e.getTags().get(0))
            .returns("td", HtmlTag::getName)
            .returns(null, HtmlTag::getText)
            .returns(Collections.emptyList(), HtmlTag::getAttributes)
            .returns(1, e -> e.getTags().size())
            .extracting(e -> e.getTags().get(0))
            .returns("button", HtmlTag::getName)
            .returns(HtmlText.builder().text("Click").tags(Collections.emptyList()).build(), HtmlTag::getText)
            .returns(Collections.emptyList(), HtmlTag::getTags)
            .returns(1, e -> e.getAttributes().size())
            .extracting(e -> e.getAttributes().get(0))
            .returns("onclick", HtmlAttribute::getName)
            .returns("server.click({ \"col\"=1, \"row\"=1 })", HtmlAttribute::getValue);
    }
}
