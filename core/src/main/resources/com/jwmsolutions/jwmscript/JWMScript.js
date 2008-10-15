(function() {

    var extend = function(a, b) {
        a = a || new Object();
        if (b) { for (var i in b) { a[i] = b[i]; } }
        return a;
    };

    var util = {
        extend : extend,

        Y : function (le) {
            return function (f) {
                return f(f);
            }(function (f) {
                    return le(function (x) {
                            return f(f)(x);
                        });
                });
        },

        relative : function(path, to) {
            to = to || document.location.toString();
            return to.toString().replace(/\/[^\/]*$/, "/")+path;
        },

        copy_ary : function(obj) {
            var ary = new Array();
            for (var i = 0; i < obj.length; i++) {
                ary[i] = obj[i];
            };
            return ary;
        },

        ary_include : function(ary, el) {
            for (var i = 0; i < ary.length; i++) {
                if (ary[i] == el) { return true; }
            }
            return false;
        },

        seek_scripts : function(el, types, cb) {
            var scripts = el.getElementsByTagName("script");
            for (var i = 0; i < scripts.length; i++) {
                var script = scripts[i];
                if (this.ary_include(types, script.getAttribute("type"))){
                    cb(script);
                }
            }
        },

        escape : function(str) {
            return str.replace(/([\"\'])/g, "\\$1").replace(/[\n\r]/g, "\\n");
        },

        exception_handle : function(callback, scope) {
            return function() {
                try {
                    callback.apply(scope || this, arguments);
                } catch (e) {
                    if (window.console) {
                        window.console.error(this.backtrace(e));
                    } else {
                        alert(this.backtrace(e));
                    }
                }
            };
        },

        backtrace : function(e) {
            return this.javaObject.getBacktrace(e);
        },

        wrapClass : function(name, classLoader) {
            var wrapper = this.javaObject.wrapClass(name, classLoader || null);
            return {
                newInstance : function() {
                    return wrapper.newInstance(this.toJavaArray(arguments));
                },
                field : function(fieldName) {
                    return wrapper.getStaticField(fieldName);
                },
                method : function(methodName) {
                    return function() {
                        return wrapper.callStaticMethod(methodName, this.toJavaArray(arguments));
                    };
                },
                on : function(object) {
                    return {
                        field : function(fieldName) {
                            return wrapper.getInstanceField(object, fieldName);
                        },
                        method : function(methodName) {
                            return function() {
                                return wrapper.callInstanceMethod(object, methodName, this.toJavaArray(arguments));
                            };
                        }
                    };
                },
                wrapper : wrapper
            };
        },

        handle : function(obj) {
            var ctx = this.javaObject.getJSHandle().getAppletContext();
            return this.wrapClass("com.jwmsolutions.jwmscript.JSHandle").newInstance(obj, ctx);
        },

        toURLArray : function(a) {
          return this.javaObject.toURLArray(this.handle(a));
        },

        toJavaArray : function(a, type) {
          return this.javaObject.toJavaArray(this.handle(a), type || null);
        }
    };

    extend(JWMScript, {
        setUtil : function(javaObject) {
            util.javaObject = javaObject;
        }
    });

    extend(JWMScript.prototype, {

        initialize : util.exception_handle(function(javaObject) {
            this.javaObject = javaObject;
            return this.register(this.types, this.setup);
        }),

        register : util.exception_handle(function(types, callback) {
            var scripting = new JWMScript.Scripting({ javaObject : this.javaObject });
            var eval = callback(scripting);
            var evaluator;
            if (typeof(eval) == 'function') {
                evaluator = util.exception_handle(eval);
            } else {
                evaluator = util.exception_handle(function(script) { eval.eval(script.innerHTML); });
            }
            document.addEventListener("DOMNodeInserted", function(event) {
               var element = event.relatedNode.lastChild;
               if (element.nodeName == "script" && util.ary_include(types, element.getAttribute("type"))) {
                   evaluator(element);
               }
            }, false);
            util.seek_scripts(document, types, evaluator);
        })

    });

    JWMScript.Scripting = function(obj) { extend(this, obj); };
    extend(JWMScript.Scripting.prototype, {
        addClassPath : function() {
            var urls = util.toURLArray(arguments);
            this.javaObject.addClassPath(urls);
        },
        wrapClass : function(name) {
            return util.wrapClass(name, this.javaObject.getClassLoader());
        },
        getHandle : function() {
            return this.javaObject.getJSHandle();
        },
        createCallback : function(invocable, object, method) {
            return util.exception_handle(function() {
                    var args = util.toJavaArray(arguments);
                    return invocable.invokeMethod(object, method, args);
            });
        }
    });

    return JWMScript;

})();
