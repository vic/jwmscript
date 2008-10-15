package com.jwmsolutions.jwmscript;

import java.net.URL;
import javax.script.Invocable;

public class Scripting implements JSHolder {

    private ClassLoader classLoader;
    private JSHandle handle;
    private Applet applet;

    public Scripting(JSHandle handle, ClassLoader classLoader, Applet applet) {
        this.handle = handle;
        this.classLoader = classLoader;
        this.applet = applet;
    }

    public void setJSHandle(JSHandle handle) {
        this.handle = handle;
    }
    
    public JSHandle getJSHandle() {
        return handle;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void addClassPath(URL ... urls) {
        URLSetPolicy policy = applet.getPolicy();
        policy.addURL(urls);
        URLClassLoader cl = (URLClassLoader) getClassLoader();
        cl.addURL(urls);
    }

}