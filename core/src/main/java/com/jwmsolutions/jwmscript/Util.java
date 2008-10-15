package com.jwmsolutions.jwmscript;

import java.net.URL;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import javax.script.Invocable;
import netscape.javascript.JSObject;
import java.lang.reflect.Array;

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

    public Object toURLAry(JSHandle jsAry) throws java.net.MalformedURLException {
        try {
        int length = ((Number) jsAry.getMember("length")).intValue();
        Object ary = Array.newInstance(URL.class, length);     
        for (int i = 0; i < length; i++) {
            Object obj = jsAry.getSlot(i);
            if (obj == null) { continue; }
            String spec = obj.toString();
            if (!spec.matches("^\\w+:.+")) {
                String location = (String) handle.eval("document.location.toString()");
                spec = location.replaceFirst("/[^/]*$", "/"+spec);
            }
            handle.alert("Doing "+i+" "+spec);
            Array.set(ary, i, new URL(spec));
        }
        handle.alert("Done with url ary "+length);
        return ary;
        }catch(Throwable e) {
            handle.alert(getBacktrace(e));
            throw new RuntimeException(e);
        }
    }

    public Object toJavaAry(JSHandle jsAry, Class javaClass) {
        int length = ((Number) jsAry.getMember("length")).intValue();
        if (javaClass == null) { javaClass = Object.class; }
        Object ary = Array.newInstance(URL.class, length);     
        for (int i = 0; i < length; i ++) {
            Object obj = jsAry.getSlot(i);
            if (JSObject.class.isAssignableFrom(javaClass) && obj instanceof JSHandle) {
                obj = ((JSHandle) obj).getJSObject();
            }
            Array.set(ary, i, obj);
        }
        return ary;
    }

    public JSHandle newHandle(JSObject jsObject) {
        return new JSHandle(jsObject, getJSHandle().getAppletContext());
    }
    
}