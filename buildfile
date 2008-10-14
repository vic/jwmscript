#!/usr/bin/env ruby
exit !!eval(DATA.read, binding) if $0 == __FILE__

repositories.remote << "http://www.ibiblio.org/maven2/"
repositories.remote << "http://repo1.maven.org/maven2/"

define 'jwmscript' do

  gh = "http://github.com/vborja/"
  gh_wikis = "http://github.com/vborja/jwmscript-example/wikis/"

  bsh = artifact("org.beanshell:bsh:jar:2.0b4")
  jruby_complete = artifact("org.jruby:jruby-complete:jar:1.1.4")

  project.version = '0.0.1'

  signed = task('signed')

  define 'ruby' do
    package(:jar).include :from => _('lib')
    package(:jar).enhance { signed.enhance [ package(:jar) ] }
  end
  
  define 'core' do
    package(:jar).enhance { signed.enhance [ package(:jar) ] }
    compile.with File.expand_path('jre/lib/plugin.jar', ENV['JAVA_HOME'])
  end

  def sign(jar)
    sh File.expand_path('bin/jarsigner', ENV['JAVA_HOME']), 
    '-storepass', 'j@v@scr1pt1ng.store', '-keypass', 'j@v@scr1pt1ng.key', 
    '-signedjar', _(jar.to_s.pathmap('examples/signed/%f')),
    jar.to_s, 'mykey'
  end

  task('sign', [:artifact] => signed) do |task, args|
    mkpath _('examples/signed')
    sign(artifact(args[:artifact])) if args[:artifact]
    signed.prerequisites.each { |jar| sign(jar) }
  end

  task('gh', [:example, :push_signed] => ['rake:default']) do |task, args|
    example = args[:example] || 'bsh/helloWorld'
    Dir.chdir(_("examples")) do
      to_push = []
      to_push |= signed.prerequisites.map { |jar| _("examples/signed/#{jar.to_s.pathmap('%f')}") } if args[:push_signed] 
      to_push << _("examples/#{example}.html") if File.exist?(_("examples/#{example}.html"))
      cmd = ['git', 'commit', '-m', ("Testing #{example} "+Time.now.to_s).inspect] + to_push
      puts cmd.join(" ")
      sh *cmd rescue nil
      sh 'git', 'push'
    end
    sh 'firefox', '-no-remote', '-P', 'devel', gh_wikis+example.downcase.gsub(/\W/, "")
  end

  task('ff', [:example] => ['rake:default']) do |task, args|
    example = args[:example] || 'bsh/helloWorld'
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
