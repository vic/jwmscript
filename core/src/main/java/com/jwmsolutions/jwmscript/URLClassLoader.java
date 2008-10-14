package com.jwmsolutions.jwmscript;

import java.net.MalformedURLException;

public class URLClassLoader extends java.net.URLClassLoader {

    public URLClassLoader() {
        super(new java.net.URL[0]);
    }

    public URLClassLoader(ClassLoader cl) {
        super(new java.net.URL[0], cl);
    }

    public void addURL(String url) throws MalformedURLException {
        super.addURL(new java.net.URL(url));
    }
    
    public void addURL(java.net.URL url) {
        super.addURL(url);
    }
}