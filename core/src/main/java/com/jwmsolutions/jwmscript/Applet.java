package com.jwmsolutions.jwmscript;

import java.net.URL;
import netscape.javascript.JSObject;

public class Applet extends java.applet.Applet {
    
    private static final long serialVersionUID = -3544417053244651886L;
    private static final String JS_RESOURCE = "com/jwmsolutions/jwmscript/JWMScript.js";

    private Util util;
    
    public void init() {
        JSHandle handle = new JSHandle(JSObject.getWindow(this), getAppletContext());
        util = new Util(handle);
        try {
            super.init();
            initPermissions();
            ClassLoader cl = getClass().getClassLoader();
            handle = (JSHandle) handle.eval(util.getResourceString(JS_RESOURCE, cl));
            util.setJSHandle(handle);
            handle.call("setUtil", util);
            newScripting(getParameter("object_id"));
        } catch (Throwable t) {
            handle.alert(util.getBacktrace(t));
        }
    }

    public Scripting newScripting(String id) {
        JSHandle instance = (JSHandle) util.getJSHandle().call("getInstance", id);
        ClassLoader cl = getClass().getClassLoader();
        Scripting scripting = new Scripting(instance, new URLClassLoader(cl));
        instance.call("initialize", scripting);
        return scripting;
    }

    private void initPermissions() {
        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
        URLSetPolicy policy = new URLSetPolicy();
        java.security.Policy.setPolicy(policy);
        policy.addPermission(new java.security.AllPermission());
        policy.addURL(url, getCodeBase(), getDocumentBase());
    }
    
}
