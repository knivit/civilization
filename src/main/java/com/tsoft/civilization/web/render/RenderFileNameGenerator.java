package com.tsoft.civilization.web.render;

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

    public Path getOutputFileName() {
        StackWalker walker = StackWalker.getInstance();
        String methodName = walker.walk(frames -> frames.skip(2).findFirst().map(StackWalker.StackFrame::getMethodName)).orElse("method");

        AtomicInteger n = order.computeIfAbsent(methodName, (s) -> new AtomicInteger(0));
        String fileName = methodName  + "_" + fileNameTemplate + "_" + n.addAndGet(1) + ".png";

        return Path.of("target", clazz.getPackageName().replace('.', '/'), clazz.getSimpleName(), fileName);
    }
}
