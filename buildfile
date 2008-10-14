#!/usr/bin/env ruby
exit !!eval(DATA.read, binding) if $0 == __FILE__

define 'jwmscript' do

  project.version = '0.0.1'

  define 'ruby' do
    package(:jar).include :from => _('lib')
  end
  
  define 'core' do
    package(:jar)
    compile.with File.expand_path('jre/lib/plugin.jar', ENV['JAVA_HOME'])
  end

  task('sign' => [project('ruby').package(:jar),
                  project('core').package(:jar)]) do |task|
    mkpath _('examples/signed')
    task.prerequisites.each do |jar|
      sh File.expand_path('bin/jarsigner', ENV['JAVA_HOME']), 
      '-storepass', 'j@v@scr1pt1ng.store', '-keypass', 'j@v@scr1pt1ng.key', 
      '-signedjar', _(jar.to_s.pathmap('examples/signed/%f')),
      jar.to_s, 'mykey'
    end
  end


  task('ff', [:example] => ['package', project('core').task('sign')]) do |task, args|
    example = args[:example] || 'bsh'
    cp _('core/src/main/resources/com/jwmsolutions/jwmscript/applet.js'), _('examples')
    sh 'firefox', '-no-remote', '-P', 'devel', _("examples/example-#{example}.html")
  end

  task('sandbox', :file) do |task, args|
    Java::Commands.java 'bsh.Interpreter', _('sandbox', args[:file].to_s), 
    :classpath => [File.expand_path("~/tmp/bsh-2.0b4.jar")]
  end
    
end

__END__
$:.unshift File.expand_path('~/hk/buildr/lib')
$:.unshift File.expand_path('~/hk/buildr/addon')
require 'rubygems'
gem 'rake', '>= 0.8.3'
require 'buildr'
Buildr.application.run
