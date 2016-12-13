require 'vertx/vertx'
require 'vertx/util/utils.rb'
# Generated from service.issue.bottleneck.TestService
module TestService
  class TestService
    # @private
    # @param j_del [::TestService::TestService] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::TestService::TestService] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::Vertx::Vertx] vertx 
    # @param [Hash{String => Object}] config 
    # @return [::TestService::TestService]
    def self.create(vertx=nil,config=nil)
      if vertx.class.method_defined?(:j_del) && config.class == Hash && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::ServiceIssueBottleneck::TestService.java_method(:create, [Java::IoVertxCore::Vertx.java_class,Java::IoVertxCoreJson::JsonObject.java_class]).call(vertx.j_del,::Vertx::Util::Utils.to_json_object(config)),::TestService::TestService)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx,config)"
    end
    # @param [::Vertx::Vertx] vertx 
    # @param [String] address 
    # @return [::TestService::TestService]
    def self.create_proxy(vertx=nil,address=nil)
      if vertx.class.method_defined?(:j_del) && address.class == String && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::ServiceIssueBottleneck::TestService.java_method(:createProxy, [Java::IoVertxCore::Vertx.java_class,Java::java.lang.String.java_class]).call(vertx.j_del,address),::TestService::TestService)
      end
      raise ArgumentError, "Invalid arguments when calling create_proxy(vertx,address)"
    end
    #  Get the version of the currently running microservice
    # @yield 
    # @return [self]
    def test
      if block_given?
        @j_del.java_method(:test, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling test()"
    end
  end
end
