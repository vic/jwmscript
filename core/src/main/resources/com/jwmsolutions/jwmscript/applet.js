JWMScript = function() {

    var extend = function(a, b) {
        a = a || new Object();
        if (b) { for (var i in b) { a[i] = b[i]; } }
        return a;
    };

    var obj = function(cfg) {
        JWMScript.addInstance(extend(this, cfg));
    };

    extend(obj, {
        addInstance : function(obj) {
            this.counter = this.counter || 0;
            this.counter += 1;
            obj.id = "JWMScript"+this.counter;
            this.instances = this.instances || {};
            this.instances[obj.id] = obj;
            return obj;
        },

        getInstance : function(id) {
            this.instances = this.instances || {};
            return this.instances[id];
        }
    });

    extend(obj.prototype, {

        applet : function() {
            var str = "";
            str += '<applet  ';
            if (this.codebase) { str += ' codebase="'+this.codebase+'" '; }
            str += ' archive="'+this.archive+'" ';
            str += ' code="com.jwmsolutions.jwmscript.Applet" ';
            str += ' width="0" height="0" mayscript="mayscript" scriptable="true" ';
            str += ' > ';
            str += '     <param name="object_id"  value="'+this.id+'"> ';
            str += '</applet> ';
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
                str += ('<embed id = "'+this.id+'" code="com.jwmsolutions.jwmscript.Applet" ');
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
                str += (' <param name="code" value="com.jwmsolutions.jwmscript.Applet" />');
                str += (' <param name="codebase" value="'+this.archive+'" />');
                str += (' <param name="mayscript" value="true" />');
                str += (' <param name="scriptable" value="true" />');
            }

            str += (' <param name="object_id" value="'+this.id+'" />');

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
JWMScript.relative = function(path, to) {
    to = to || document.location.toString();
    return to.toString().replace(/\/[^\/]*$/, "/")+path;
};
