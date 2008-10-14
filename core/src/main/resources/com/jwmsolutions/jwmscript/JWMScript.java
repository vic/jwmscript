(function() {

    var extend = function(a, b) {
        a = a || new Object();
        if (b) { for (var i in b) { a[i] = b[i]; } }
        return a;
    };

    var util = {
        extend : extend,

        handle : function(callback, scope) {
            return function() {
                try {
                    callback.apply(scope || this, arguments);
                } catch (e) {
                    alert(util.backtrace(e));
                }
            };
        },

        backtrace : function(e) {
            return this.root.javaObject.getBacktrace(e);
        },

        wrapClass : function(name, classLoader) {
            var wrapper = this.root.javaObject.wrapClass(name, classLoader || null);
            return {
                newInstance : function() {
                    return wrapper.callConstructor(util.toJavaArray(arguments));
                },
                field : function(fieldName) {
                    return wrapper.getField(fieldName);
                },
                method : function(methodName) {
                    return function() {
                        return wrapper.callMethod(methodName, util.toJavaArray(arguments));
                    };
                },
                wrapper : wrapper
            };
        },

        toURLArray : function(a) {
          var urlArray = java.lang.reflect.Array.newInstance(java.net.URL, a.length);
          for (var i = 0; i < a.length; i++) {
              var url = a[i];
              java.lang.reflect.Array.set(urlArray, i, (typeof url == "string") ? new java.net.URL(url) : url);
          }
          return urlArray;
        },

        toJavaArray : function(a, type) {
          type = type || java.lang.Object;
          var array = java.lang.reflect.Array.newInstance(type, a.length);
          for (var i = 0; i < a.length; i++) {
              java.lang.reflect.Array.set(array, i, a[i]);
          }
          return array;
        }
    };

    var JWMScript = function() { util.root = this; };
    extend(JWMScript.prototype, {
        initialize : function(javaObject) {
            this.javaObject = javaObject;
            window.JWMScript.init(this);
        },

        register : util.handle(function(callback) {
            var scripting = new JWMScript.Scripting();
            var cls = util.wrapClass("org.mozilla.addons.jrubyfox.Scripting");
            scripting.javaObject = cls.newInstance(scripting);
            callback(scripting);
        })

    });

    JWMScript.Scripting = function(obj) { extend(this, obj); };
    extend(JWMScript.Scripting.prototype, {
        addClassPath : function() {
            this.javaObject.addClassPath(util.toURLArray(arguments));
        },
        wrapClass : function(name) {
            return util.wrapClass(name, this.javaObject.getClassLoader());
        }
    });

    return function() {
        return new JWMScript();
    };

})();
