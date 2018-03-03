package com.tsoft.civilization.web;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class TestGameServer {
    private ScriptEngine engine;
    private String testJSObjectName;

    public TestJavaScriptResult parseJSON(String json) throws ScriptException {
        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("JavaScript");
        engine.put("TestGameServer", this);

        Object result = engine.eval("JSON.parse('" + json + "')");
        return new TestJavaScriptResult((ScriptObjectMirror)result);
    }

    public void testJS(String testJSFileName, String testJSObjectName) throws ScriptException {
        this.testJSObjectName = testJSObjectName;

        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("JavaScript");
        engine.put("TestGameServer", this);
        engine.eval("function load(fileName) { TestGameServer.load(fileName); }");
        engine.eval("function log(message) { TestGameServer.log(message); }");
        engine.eval("function jsUnitLog(message, methodName) { TestGameServer.jsUnitLog(message, methodName); }");

        load(testJSFileName);

        engine.eval("test(" + testJSObjectName + ")");
    }

    public void load(String fileName) throws ScriptException {
        fileName = "js/" + fileName;
        try {
            // try to load a file from 'test' package
            URL url = TestGameServer.class.getResource(fileName);
            if (url == null) {
                // then from 'main' package
                url = GameServer.class.getResource(fileName);
            }
            assertTrue("File '" + TestGameServer.class.getPackage().getName() + "." + fileName + "' not found", url != null);

            engine.eval(new FileReader(url.getFile()));
        } catch(FileNotFoundException e) {
            throw new RuntimeException("Error loading javascript file: " + fileName, e);
        }
    }

    public void log(String message) {
        System.out.println(message);
    }

    public void jsUnitLog(String message, String methodName) {
        System.out.print(message + testJSObjectName + "." + methodName + "(): ");
    }
}
