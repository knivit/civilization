package com.tsoft.civilization.helper.html;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlInput {

    @Getter
    private int pos;

    @Getter
    private int level;

    private String data;

    HtmlInput() { }

    public static HtmlInput of(String data) {
        HtmlInput input = new HtmlInput();
        input.data = data;
        return input;
    }

    public boolean ended() {
        return (data == null) || (pos >= data.length());
    }

    public void skipBlank() {
        while (!ended()) {
            char ch = data.charAt(pos);
            if (ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r') {
                pos++;
            } else {
                break;
            }
        }
    }

    public Character readChar() {
        skipBlank();

        if (!ended()) {
            char ch = data.charAt(pos);
            pos++;
            return ch;
        }
        return null;
    }

    public String readToken() {
        skipBlank();

        int start = pos;
        while (!ended()) {
            char ch = data.charAt(pos);
            if (ch > ' ' && ch != '<' && ch != '/' && ch != '>' && ch != '=') {
                pos++;
            } else {
                break;
            }
        }
        return data.substring(start, pos);
    }

    public String readValue() {
        skipBlank();

        Character quot = peekChar();
        if (quot == null) {
            return null;
        }

        if (!(quot == '\'' || quot == '"')) {
            return readToken();
        }

        // skip the quot
        readChar();

        int start = pos;
        while (!ended()) {
            char ch = data.charAt(pos);
            pos++;

            if (ch == quot) {
                break;
            }
        }
        return data.substring(start, pos - 1);
    }

    public HtmlText readText(Function<HtmlInput, HtmlTag> tagReader) {
        int start = pos;
        StringBuilder buf = new StringBuilder();
        List<HtmlTag> tags = null;

        while (!ended()) {
            char ch = data.charAt(pos);
            if (ch != '<') {
                if (ch != '\n') {
                    buf.append(ch);
                }
                pos ++;
            } else {
                // "... text ... <sub tag /> ... text ..."
                Character next = (pos + 1 < data.length()) ? data.charAt(pos + 1) : null;
                if (next != null && next != '/') {
                    HtmlInput input = new HtmlInput();
                    input.pos = pos;
                    input.data = data;
                    input.level = level + 1;

                    HtmlTag tag = tagReader.apply(input);
                    if (tags == null) {
                        tags = new ArrayList<>();
                    }
                    tags.add(tag);

                    buf.append('[').append(tag.getName()).append(']');
                    start = input.pos;
                    pos = start;
                } else {
                    break;
                }
            }
        }

        return HtmlText.builder()
            .text(buf.toString())
            .tags((tags == null) ? Collections.emptyList() : tags)
            .build();
    }

    public Character peekChar() {
        skipBlank();
        if (!ended()) {
            return data.charAt(pos);
        }
        return null;
    }

    public boolean startWith(String s) {
        return data.startsWith(s, pos);
    }

    public boolean closeTag(String name) {
        skipBlank();

        String val = "</" + name + ">";
        if (data.startsWith(val, pos)) {
            pos += val.length();
            return true;
        }
        return false;
    }

    @Test
    public void peek_char_from_null() {
        HtmlInput input = HtmlInput.of(null);

        assertThat(input.peekChar()).isNull();
    }

    @Test
    public void peek_char_from_empty() {
        HtmlInput input = HtmlInput.of("");

        assertThat(input.peekChar()).isNull();
    }

    @Test
    public void peek_char() {
        HtmlInput input = HtmlInput.of("a");

        assertThat(input.peekChar()).isEqualTo('a');
    }

    @Test
    public void read_token() {
        HtmlInput input = HtmlInput.of(" test ");

        assertThat(input.readToken()).isEqualTo("test");
    }

    @Test
    public void read_text() {
        HtmlInput input = HtmlInput.of(" test read text ");

        assertThat(input.readText(null))
            .isNotNull()
            .extracting(HtmlText::getText)
            .isEqualTo(" test read text ");
    }

    @Test
    public void read_text_with_tags() {
        HtmlInput input = HtmlInput.of(" test read text <tagA /> with <tagB /> tags ");

        assertThat(input.readText(HtmlParser::readTag))
            .isNotNull()
            .returns(" test read text [tagA] with [tagB] tags ", HtmlText::getText)
            .returns(List.of(
                HtmlTag.builder().name("tagA").attributes(Collections.emptyList()).tags(Collections.emptyList()).build(),
                HtmlTag.builder().name("tagB").attributes(Collections.emptyList()).tags(Collections.emptyList()).build()
            ), HtmlText::getTags);
    }

    @Test
    public void read_value() {
        HtmlInput input = HtmlInput.of(" ' value { 1 } '");

        assertThat(input.readValue()).isEqualTo(" value { 1 } ");
    }

    @Test
    public void close_tag_fails() {
        HtmlInput input = HtmlInput.of(" </tag");

        assertThat(input.closeTag("tag")).isFalse();
    }

    @Test
    public void close_tag() {
        HtmlInput input = HtmlInput.of(" </tag> val");

        assertThat(input.closeTag("tag")).isTrue();
        assertThat(input.readToken()).isEqualTo("val");
    }

    @Test
    public void skip_blank() {
        HtmlInput input = HtmlInput.of("  \n \t \r ");
        input.skipBlank();

        assertThat(input.ended()).isTrue();
    }
}
