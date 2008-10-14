JWMScript = function(cfg) {

    JWMScript.initialize = JWMScript.initialize || function(jwmscript) {
        return jwmscript.register.apply(jwmscript, [cfg.types, cfg.setup]);
    };

};

JWMScript.applet = function(archive) {
    var str = ""
    var nav = navigator.appName;
    if (nav.match(/Netscape/)) {
        str += ('<embed code="com.jwmsolutions.jwmscript.JWMScriptApplet" ');
        str += (' archive="'+archive+'"');
        str += (' width="0" height="0" ');
        str += (' mayscript="true" ');
        str += (' pluginspage="http://java.sun.com/javase/downloads/ea.jsp" ');
        str += (' type="application/x-java-applet;version=1.6" >');
    } else {
        str += ('<object classid="clsid:CAFEEFAC-0016-0000-0000-ABCDEFFEDCBA" ');
        str += (' width="10" height="10"');
        str += (' codebase="http://java.sun.com/products/plugin/autodl/jinstall-1_5_0-windows-i586.cab#Version=1,5,0,0">');
        str += (' <param name="code" value="com.jwmsolutions.jwmscript.JWMScriptApplet" />');
        str += (' <param name="codebase" value="'+archive+'" />');
        str += (' <param name="mayscript" value="true" />');
    }

    if (nav.match(/Netscape/)) {
        str += ('</embed>');
    } else {
        str += ('</object>');
    }
    return str;
}
