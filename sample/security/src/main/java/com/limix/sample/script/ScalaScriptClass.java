package com.limix.sample.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScalaScriptClass {

    public static void main(String[] args){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine;
        engine = manager.getEngineByName("scala");
        try {
            engine.eval("print okey");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
