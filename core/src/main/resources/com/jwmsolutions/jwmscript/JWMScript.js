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
                if (util.ary_include(types, script.getAttribute("type"))){
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
                        window.console.error(util.backtrace(e));
                    } else {
                        alert(util.backtrace(e));
                    }
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
                    return wrapper.newInstance(util.toJavaArray(arguments));
                },
                field : function(fieldName) {
                    return wrapper.getStaticField(fieldName);
                },
                method : function(methodName) {
                    return function() {
                        return wrapper.callStaticMethod(methodName, util.toJavaArray(arguments));
                    };
                },
                on : function(object) {
                    return {
                        field : function(fieldName) {
                            return wrapper.getInstanceField(object, fieldName);
                        },
                        method : function(methodName) {
                            return function() {
                                return wrapper.callInstanceMethod(object, methodName, util.toJavaArray(arguments));
                            };
                        }
                    };
                },
                wrapper : wrapper
            };
        },

        toURLArray : function(a) {
          var urlArray = java.lang.reflect.Array.newInstance(java.net.URL, a.length);
          for (var i = 0; i < a.length; i++) {
              var url = a[i];
              if (typeof(url) == "string") {
                  if (!url.match(/^\w+:/)) { url = util.relative(url); }
                  url = new java.net.URL(url);
              }
              java.lang.reflect.Array.set(urlArray, i, url);
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

    extend(JWMScript.prototype, {

        initialize : function(javaObject) {
            alert("JS INIT");
            this.javaObject = javaObject;
            alert("initing permissions");
            javaObject.initPermissions(window);
            alert("Permissions inited");
            return self.register(this.types, this.setup);
        },

        register : util.exception_handle(function(types, callback) {
            var scripting = new JWMScript.Scripting();
            var ctx = this.javaObject.getAppletContext();
            var handle = util.wrapClass("com.jwmsolutions.jwmscript.JSHandle").newInstance(scripting, ctx);
            scripting.javaObject = util.wrapClass("com.jwmsolutions.jwmscript.Scripting").newInstance(handle);
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
            urls = util.toURLArray(arguments);
            java.security.Policy.getPolicy().addURL(urls);
            this.javaObject.getClassLoader().addURL(urls);
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

    return function(javaObject) {
        var instances = JWMScript.instances || {};
        var id = javaObject.getParameter("object_id");
        alert("initing javascript object "+id);
        var obj = instances[id];
        return obj.initialize(javaObject);
    };

})();
