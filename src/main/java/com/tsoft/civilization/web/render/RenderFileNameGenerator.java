package com.tsoft.civilization.web.render;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RenderFileNameGenerator {

    private final Class<?> clazz;
    private final String fileNameTemplate;
    private final Map<String, AtomicInteger> order = new HashMap<>();

    public RenderFileNameGenerator(Class<?> clazz, String fileNameTemplate) {
        this.clazz = clazz;
        this.fileNameTemplate = fileNameTemplate;
    }

    public Path getOutputFileName(String ext) {
        StackWalker walker = StackWalker.getInstance();
        String methodName = walker.walk(frames -> frames
                .filter(s -> s.getClassName().endsWith("Test"))
                .findFirst().map(StackWalker.StackFrame::getMethodName))
            .orElse("method");

        AtomicInteger n = order.computeIfAbsent(methodName, (s) -> new AtomicInteger(0));
        String fileName = methodName  + "_" + fileNameTemplate + "_" + n.addAndGet(1) + ext;

        Path folder = Path.of("target", clazz.getPackageName().replace('.', '/'), clazz.getSimpleName());

        try {
            Files.createDirectories(folder);
        } catch (Exception e) {
            throw new IllegalStateException("Can't create folder " + folder, e);
        }

        return folder.resolve(fileName);
    }
}
