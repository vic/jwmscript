JWMScript = function() {

    var args = arguments;

    JWMScript.initialize = JWMScript.initialize || function(jwmscript) {
        return jwmscript.register.apply(jwmscript, args);
    };

    var nav = navigator.appName;
    if (nav.match(/Netscape/)) {
        document.write('<embed code="com.jwmsolutions.jwmscript.JWMScriptApplet" ');
        document.write(' archive="signed/jwmscript-core-0.0.1.jar"');
        document.write(' width="0" height="0" ');
        document.write(' mayscript="true" ');
        document.write(' pluginspage="http://java.sun.com/javase/downloads/ea.jsp" ');
        document.write(' type="application/x-java-applet;version=1.6" >');
    } else {
        document.write('<object classid="clsid:CAFEEFAC-0016-0000-0000-ABCDEFFEDCBA" ');
        document.write(' width="10" height="10"');
        document.write(' codebase="http://java.sun.com/products/plugin/autodl/jinstall-1_5_0-windows-i586.cab#Version=1,5,0,0">');
        document.write(' <param name="code" value="com.jwmsolutions.jwmscript.JWMScriptApplet" />');
        document.write(' <param name="codebase" value="signed/jwmscript-core-0.0.1.jar" />');
        document.write(' <param name="mayscript" value="true" />');
    }

    if (nav.match(/Netscape/)) {
        document.write('</embed>');
    } else {
        document.write('</object>');
    }

};
