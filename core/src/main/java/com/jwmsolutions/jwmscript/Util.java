package com.jwmsolutions.jwmscript;

import java.net.URL;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import javax.script.Invocable;
import netscape.javascript.JSObject;

public class Util implements JSHolder {
    
    private JSHandle handle;

    public Util(JSHandle handle) {
        this.handle = handle;
    }

    public JSHandle getJSHandle() {
        return handle;
    }

    public void setJSHandle(JSHandle handle) {
        this.handle = handle;
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
        if (loader == null) { loader = JSHandle.class.getClassLoader(); }
        Class cls = loader.loadClass(name);
        return new ClassWrapper(cls);
    }


    public String getResourceString(String resource, ClassLoader classLoader) throws Exception {
        URL url = classLoader.getResource(resource);
        return readURLString(url);
    }

    public String readURLString(URL url) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
        StringBuffer buff = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) { buff.append(inputLine).append("\n"); }
        in.close();
        return buff.toString();
    }
    
    public JSHandle jsFunction(JSHandle handle, Invocable ivk, Object object, String name) {
        return (JSHandle) handle.call("createCallback", new Object[] { ivk, object, name });
    }

    public URL[] toURLArray(JSHandle jsAry) throws java.net.MalformedURLException {
        int length = Integer.parseInt(jsAry.getMember("length").toString());
        URL[] ary = new URL[length];
        for (int i = 0; i < length; i++) {
            Object obj = jsAry.getSlot(i);
            if (obj == null) { continue; }
            ary[i] = new URL(obj.toString());
        }
        return ary;
    }

    public Object[] toObjectArray(JSHandle jsAry, Class javaClass) {
        if (javaClass == null) { javaClass = Object.class; }
        int length = Integer.parseInt(jsAry.getMember("length").toString());
        Object[] ary = new Object[length];
        for (int i = 0; i < length; i ++) {
            Object obj = jsAry.getSlot(i);
            if (JSObject.class.isAssignableFrom(javaClass) && obj instanceof JSHandle) {
                obj = ((JSHandle) obj).getJSObject();
            }
            ary[i] = obj;
        }
        return ary;
    }
    
}