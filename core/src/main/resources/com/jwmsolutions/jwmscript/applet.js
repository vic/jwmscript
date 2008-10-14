JWMScript = function(cfg) {

    JWMScript.initialize = JWMScript.initialize || function(jwmscript) {
        return jwmscript.register.apply(jwmscript, [cfg.types, cfg.setup]);
    };

    JWMScript.writeApplet(cfg.archive);
};

JWMScript.writeApplet = function (archive) {
    var nav = navigator.appName;
    if (nav.match(/Netscape/)) {
        document.write('<embed code="com.jwmsolutions.jwmscript.JWMScriptApplet" ');
        document.write(' archive="'+archive+'"');
        document.write(' width="0" height="0" ');
        document.write(' mayscript="true" ');
        document.write(' pluginspage="http://java.sun.com/javase/downloads/ea.jsp" ');
        document.write(' type="application/x-java-applet;version=1.6" >');
    } else {
        document.write('<object classid="clsid:CAFEEFAC-0016-0000-0000-ABCDEFFEDCBA" ');
        document.write(' width="10" height="10"');
        document.write(' codebase="http://java.sun.com/products/plugin/autodl/jinstall-1_5_0-windows-i586.cab#Version=1,5,0,0">');
        document.write(' <param name="code" value="com.jwmsolutions.jwmscript.JWMScriptApplet" />');
        document.write(' <param name="codebase" value="'+archive+'" />');
        document.write(' <param name="mayscript" value="true" />');
    }

    if (nav.match(/Netscape/)) {
        document.write('</embed>');
    } else {
        document.write('</object>');
    }
};
