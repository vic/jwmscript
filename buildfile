#!/usr/bin/env ruby
exit !!eval(DATA.read, binding) if $0 == __FILE__

repositories.remote << "http://www.ibiblio.org/maven2/"
repositories.remote << "http://repo1.maven.org/maven2/"

define 'jwmscript' do

  bsh = artifact("org.beanshell:bsh:jar:2.0b4")
  jruby_complete = artifact("org.jruby:jruby-complete:jar:1.1.4")

  project.version = '0.0.1'

  define 'ruby' do
    package(:jar).include :from => _('lib')
  end
  
  define 'core' do
    package(:jar)
    compile.with File.expand_path('jre/lib/plugin.jar', ENV['JAVA_HOME'])
  end

  task('sign' => [project('ruby').package(:jar),
                  project('core').package(:jar),
                  bsh, jruby_complete ]) do |task|
    mkpath _('examples/signed')
    task.prerequisites.each do |jar|
      sh File.expand_path('bin/jarsigner', ENV['JAVA_HOME']), 
      '-storepass', 'j@v@scr1pt1ng.store', '-keypass', 'j@v@scr1pt1ng.key', 
      '-signedjar', _(jar.to_s.pathmap('examples/signed/%f')),
      jar.to_s, 'mykey'
    end
  end


  task('ff', [:example] => ['package', 'sign']) do |task, args|
    example = args[:example] || 'bsh'
    sh 'firefox', '-no-remote', '-P', 'devel', _("examples/#{example}.html")
  end

  task('sandbox', :file) do |task, args|
    Java::Commands.java 'bsh.Interpreter', _('sandbox', args[:file].to_s), 
    :classpath => [File.expand_path("~/tmp/bsh-2.0b4.jar")]
  end
    
end

task('default' => ['jwmscript:package', 'jwmscript:sign'])

__END__
$:.unshift File.expand_path('~/hk/buildr/lib')
$:.unshift File.expand_path('~/hk/buildr/addon')
require 'rubygems'
gem 'rake', '>= 0.8.3'
require 'buildr'
Buildr.application.run
