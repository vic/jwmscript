package com.jwmsolutions.jwmscript;

import netscape.javascript.JSObject;
import netscape.javascript.JSException;
import java.applet.Applet;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.InputStream;
import javax.script.Invocable;

public class JWMScriptApplet extends Applet implements JSHolder {

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
        StringBuffer buff = new StringBuffer();
        InputStream is = url.openStream();
        byte[] bytes = new byte[is.available()];
        int offset = 0;
        for (int read = -1; 0 < (read = is.read(bytes, offset, bytes.length)); offset += read) {
            buff.append(new String(bytes));
        }
        return buff.toString();
    }

    public void init() {
        try {
            JSObject window = JSObject.getWindow(this);
            this.handle = new JSHandle(window, getAppletContext());
            URLSetPolicy policy = new URLSetPolicy();
            java.security.Policy.setPolicy(policy);
            policy.addPermission(new java.security.AllPermission());
            String code = getResourceString(JS_RESOURCE);
            JSObject constructor = (JSObject) window.eval(code);
            JSObject jsObject = (JSObject) constructor.call("call", new Object[0]);
            this.handle = new JSHandle(jsObject, getAppletContext());
            jsObject.call("initialize", new Object[] { this });
        } catch (Throwable t) {
            alertException(t);
        }
    }

    public void alert(Object message) {
        String str = String.valueOf(message);
        str = str.replaceAll("[\"\']", "\\\\$0").replaceAll("[\n\f]", "\\\\n");
        handle.eval("alert(\""+str+"\");");
    }

    private void alertException(Throwable e) {
        StringWriter writer = new StringWriter();
        PrintWriter print = new PrintWriter(writer, true);
        e.printStackTrace(print);
        alert(writer.toString());
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
