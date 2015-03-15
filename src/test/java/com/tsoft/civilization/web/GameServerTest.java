package com.tsoft.civilization.web;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class GameServerTest {
    private ScriptEngine engine;
    private String testJSObjectName;

    @Test
    public void dummy() {
        assertTrue(true);
    }

    public void parseJSON(String JSONResponse) {
        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("JavaScript");
        engine.put("GameServerTest", this);
    }

    public void testJS(String testJSFileName, String testJSObjectName) throws ScriptException {
        this.testJSObjectName = testJSObjectName;

        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("JavaScript");
        engine.put("GameServerTest", this);
        engine.eval("function load(fileName) { GameServerTest.load(fileName); }");
        engine.eval("function log(message) { GameServerTest.log(message); }");
        engine.eval("function jsUnitLog(message, methodName) { GameServerTest.jsUnitLog(message, methodName); }");

        load(testJSFileName);

        engine.eval("test(" + testJSObjectName + ")");
    }

    public void load(String fileName) throws ScriptException {
        fileName = "js/" + fileName;
        try {
            // try to load a file from 'test' package
            URL url = GameServerTest.class.getResource(fileName);
            if (url == null) {
                // then from 'main' package
                url = GameServer.class.getResource(fileName);
            }
            assertTrue("File '" + GameServerTest.class.getPackage().getName() + "." + fileName + "' not found", url != null);

            engine.eval(new FileReader(url.getFile()));
        }
        catch(FileNotFoundException e) {
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
