module JWMScript

  class JSCallback
    include javax.script.Invocable

    def initialize(obj, method)
      @object, @method = obj, method
    end
    
    def invokeMethod(object, method, args)
      block = nil # TODO obtain block
      @object.send(@method, *args, &block)
    end
    
    def js_function(object = nil, method = nil)
      handle = $jwmscript.javaObject.jsFunction self, object, method
      handle.getJSObject
    end
  end

  module JSHandle

    def jwmscript
      JWMScript.jwmscript
    end

    def window
      JWMScript.window
    end

    def document
      JWMScript.document
    end

    def [](name)
      if name.kind_of? Integer
        getSlot(name)
      else
        getMember(name)
      end
    end
    
    def []=(name, value)
      if name.kind_of? Integer
        setSlot(name)
      else
        setMember(name, value)
      end
    end
    
    def method_missing(name, *args, &block)
      name = name.to_s
      if name =~ /=$/
        self[$`] = args.first
      elsif name =~ /\?$/
        isDefined($`)
      elsif isFunction(name)
        args << block if block
        args = args.map do |arg|
          if Proc === arg || Method === arg
            JSCallback.new(arg, :call).js_function(getJSObject, name)
          else
            arg
          end
        end
        call(name, args.to_java(java.lang.Object))
      else
        self[name]
      end
    end

  end # JSHandle

  $jwmscript.class.module_eval { include JSHandle }

  class << self
    
    def jwmscript
      @jwmscript ||= $jwmscript
    end

    def window
      @window ||= $jwmscript.getWindow
    end

    def document
      @document ||= $jwmscript.getDocument
    end
    
  end

end # JWMScript
