JWMScript = function() {

    var extend = function(a, b) {
        a = a || new Object();
        if (b) { for (var i in b) { a[i] = b[i]; } }
        return a;
    };

    var obj = function(cfg) {
        JWMScript.instances = JWMScript.instances || {};
        JWMScript.counter = JWMScript.counter || 0;
        JWMScript.counter += 1;
        this.id = "JWMScript"+JWMScript.counter;
        JWMScript.instances[this.id] = this;
        extend(this, cfg);
    };
    extend(obj.prototype, {

        initialize : function(jwmscript) {
            return jwmscript.register.apply(jwmscript, [this.types, this.setup]);
        },

        applet : function() {
            var str = "";
            str += ' <APPLET ';
            str += ' ARCHIVE="'+this.archive+'" ';
            str += ' CODE="com.jwmsolutions.jwmscript.JWMScriptApplet" ';
            str += ' ALT="JWMScript Applet" ';
            str += ' NAME="'+this.id+'" ';
            str += ' WIDTH="0" HEIGHT="0" ';
            str += ' > ';
            str += '     <PARAM NAME="object_id"  VALUE="'+this.id+'"> ';
            str += ' </APPLET> ';
            return str;
        },

        appletOld : function() {
            if (!this.archive) {
                alert("You must set the archive property");
                return null;
            }
            var str = "";
            var nav = navigator.appName;
            if (nav.match(/Netscape/)) {
                str += ('<embed id = "'+this.id+'" code="com.jwmsolutions.jwmscript.JWMScriptApplet" ');
                str += (' archive="'+this.archive+'"');
                str += (' width="0" height="0" ');
                str += (' mayscript="true" ');
                str += (' scriptable="true" ');
                str += (' pluginspage="http://java.sun.com/javase/downloads/ea.jsp" ');
                str += (' type="application/x-java-applet;version=1.6" >');
            } else {
                str += ('<object id = "'+this.id+'" classid="clsid:CAFEEFAC-0016-0000-0000-ABCDEFFEDCBA" ');
                str += (' width="10" height="10"');
                str += (' codebase="http://java.sun.com/products/plugin/autodl/jinstall-1_5_0-windows-i586.cab#Version=1,5,0,0">');
                str += (' <param name="code" value="com.jwmsolutions.jwmscript.JWMScriptApplet" />');
                str += (' <param name="codebase" value="'+this.archive+'" />');
                str += (' <param name="mayscript" value="true" />');
                str += (' <param name="scriptable" value="true" />');
            }

            if (nav.match(/Netscape/)) {
                str += ('</embed>');
            } else {
                str += ('</object>');
            }
            return str;
        } // applet
    });

    return obj;
};

JWMScript = JWMScript();

