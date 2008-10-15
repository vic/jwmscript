package com.jwmsolutions.jwmscript;

import java.net.URL;
import javax.script.Invocable;

public class Scripting implements JSHolder {

    private ClassLoader classLoader;
    private JSHandle handle;

    public Scripting(JSHandle handle, ClassLoader classLoader) {
        this.handle = handle;
        this.classLoader = classLoader;
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
        URLSetPolicy policy = (URLSetPolicy) java.security.Policy.getPolicy();
        policy.addURL(urls);
        URLClassLoader cl = (URLClassLoader) getClassLoader();
        cl.addURL(urls);
    }

}