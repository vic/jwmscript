package com.jwmsolutions.jwmscript;

import java.applet.AppletContext;
import netscape.javascript.JSObject;
import sun.plugin.viewer.context.NetscapeAppletContext;

public class JSHandle {

    private AppletContext appletContext;
    
    private JSObject jsObject;

    public JSHandle(JSObject jsObject, AppletContext ctx) {
        this.jsObject = jsObject;
        this.appletContext = ctx;
        if (jsObject instanceof sun.plugin.javascript.navig5.JSObject) {
            sun.plugin.javascript.navig5.JSObject obj = (sun.plugin.javascript.navig5.JSObject) jsObject;
            obj.setNetscapeAppletContext((NetscapeAppletContext) ctx);
        }
    }

    public AppletContext getAppletContext() {
        return appletContext;
    }
    
    public JSObject getJSObject() {
        return this.jsObject;
    }

    public Object eval(String javascript) {
        Object obj = getJSObject().eval(javascript);
        if (obj instanceof JSObject) { 
            obj = new JSHandle((JSObject) obj, getAppletContext());
        }
        return obj;
    }

    public Object call(String method, Object ... args) {
        Object[] argv = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof JSHandle) {
                arg = ((JSHandle) arg).getJSObject();
            }
            argv[i] = arg;
        }
        Object obj = getJSObject().call(method, argv);
        if (obj instanceof JSObject) {
            obj = new JSHandle((JSObject) obj, getAppletContext());
        }
        return obj;
    }

    public void alert(Object msg) {
        getWindow().call("alert", msg);
    }

    public JSHandle getWindow() {
        JSObject obj = (JSObject) getJSObject().eval("window");
        return new JSHandle(obj, getAppletContext());
    }

    public JSHandle getDocument() {
        JSObject obj = (JSObject) getJSObject().eval("document");
        return new JSHandle(obj, getAppletContext());
    }

    public boolean isFunction(String name) {
        return "function".equals(getJSObject().eval("typeof("+name+")"));
    }

    public boolean isUndefined(String name) {
        return "undefined".equals(getJSObject().eval("typeof("+name+")"));
    }

    public Object getSlot(int idx) {
        Object obj = getJSObject().getSlot(idx);
        if (obj instanceof JSObject) {
            obj = new JSHandle((JSObject)obj, getAppletContext());
        }
        return obj;
    }

    public void setSlot(int idx, Object obj) {
        if (obj instanceof JSHandle) {
            obj = ((JSHandle) obj).getJSObject();
        }
        getJSObject().setSlot(idx, obj);
    }

    public Object getMember(String name) {
        Object obj = getJSObject().getMember(name);
        if (obj instanceof JSObject) {
            obj = new JSHandle((JSObject) obj, getAppletContext());
        }
        return obj;
    }

    public void setMember(String name, Object obj) {
        if (obj instanceof JSHandle) {
            obj = ((JSHandle) obj).getJSObject();
        }
        getJSObject().setMember(name, obj);
    }

    public void removeMember(String name) {
        getJSObject().removeMember(name);
    }

    public String toString() {
        return getJSObject().toString();
    }

    public boolean equals(Object other) {
        if (other instanceof JSHandle) {
            other = ((JSHandle) other).getJSObject();
        }
        return getJSObject().equals(other);
    }
    
}