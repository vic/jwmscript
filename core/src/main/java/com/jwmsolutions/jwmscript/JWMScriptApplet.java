package com.jwmsolutions.jwmscript;

import java.applet.Applet;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;

import netscape.javascript.JSObject;

public class JWMScriptApplet extends Applet implements JSHolder {
    
    private static final long serialVersionUID = -3544417053244651886L;
    private static final String JS_RESOURCE = "com/jwmsolutions/jwmscript/JWMScript.js";
    private JSHandle handle;
    
    public JSHandle getJSHandle() {
        return handle;
    }

    public String getResourceString(String resource) throws Exception {
        URL url = getClass().getClassLoader().getResource(resource);
        return readURLString(url);
    }

    private String readURLString(URL url) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
        StringBuffer buff = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) { buff.append(inputLine).append("\n"); }
        in.close();
        return buff.toString();
    }

    public void init() {
        handle = new JSHandle(JSObject.getWindow(this), getAppletContext());
        try {
            initPermissions();
            JSHandle jwmscript = (JSHandle) handle.eval(getResourceString(JS_RESOURCE));
            handle = (JSHandle) jwmscript.call("getInstance", getParameter("object_id"));
            handle.call("initialize", this);
        } catch (Throwable t) {
            handle.alert(getBacktrace(t));
        }
    }

    public void initPermissions() {
        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
        URLSetPolicy policy = new URLSetPolicy();
        java.security.Policy.setPolicy(policy);
        policy.addPermission(new java.security.AllPermission());       
        policy.addURL(url);
        handle.alert("Inited Permissions for "+url);
    }

    public String getBacktrace(Object o) {
        if (o instanceof Throwable) {
            Throwable e = (Throwable) o;
            StringWriter writer = new StringWriter();
            PrintWriter print = new PrintWriter(writer, true);
            e.printStackTrace(print);
            return writer.toString();
        } else {
            return String.valueOf(o);
        }
    }

    public ClassWrapper wrapClass(String name, ClassLoader loader) throws Exception {
        if (loader == null) { loader = getClass().getClassLoader(); }
        Class cls = loader.loadClass(name);
        return new ClassWrapper(cls);
    }

}
