package com.tsoft.civilization.web.render;

import com.tsoft.civilization.web.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class HtmlRender {

    private final RenderFileNameGenerator fileNameGenerator;

    public static HtmlRender of(Object testClassObj) {
        return new HtmlRender(testClassObj);
    }

    private HtmlRender(Object testClassObj) {
        fileNameGenerator = new RenderFileNameGenerator(testClassObj.getClass(), "response");
    }

    public void saveToFile(Response response) {
        Path outputFileName = fileNameGenerator.getOutputFileName(".html");

        try {
            Files.writeString(outputFileName, new String(response.getContent(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Error writing to a file {}", outputFileName.toFile().getAbsolutePath(), e);
            throw new IllegalStateException(e);
        }
    }
}
