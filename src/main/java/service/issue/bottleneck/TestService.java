package service.issue.bottleneck;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import service.issue.bottleneck.impl.TestServiceImpl;



@VertxGen
@ProxyGen
public interface TestService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "test-eb-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "service.test";

	static TestService create(Vertx vertx, JsonObject config) {
		return new TestServiceImpl(vertx, config);
	}

	static TestService createProxy(Vertx vertx, String address) {
		return new TestServiceVertxEBProxy(vertx, address);
	}

	/**
	 * Get the version of the currently running microservice
	 * 
	 * @param versionResultHandler
	 * @return The microservice version
	 */
	@Fluent
	public TestService test(Handler<AsyncResult<String>> handler);
}
