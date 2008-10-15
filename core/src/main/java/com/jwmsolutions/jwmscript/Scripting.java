package com.jwmsolutions.jwmscript;

import java.net.URL;
import javax.script.Invocable;

public class Scripting implements JSHolder {

    private URLClassLoader classLoader;
    private JSHandle handle;
    
    public JSHandle getJSHandle() {
        return handle;
    }
    
    public Scripting(JSHandle handle) {
        this.handle = handle;
        this.classLoader = new URLClassLoader();
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
    
    public JSHandle jsFunction(Invocable ivk, Object object, String name) {
        return (JSHandle) handle.call("createCallback", new Object[] { ivk, object, name });
    }

}