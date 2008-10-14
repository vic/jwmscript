package com.jwmsolutions.jwmscript;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class URLClassLoader extends java.net.URLClassLoader {

    public URLClassLoader() {
        super(new java.net.URL[0]);
    }

    public URLClassLoader(ClassLoader cl) {
        super(new java.net.URL[0], cl);
    }

    public void addURL(java.net.URL ... urls) {
        for (URL url : urls) { 
            super.addURL(url);
        }
    }

}
