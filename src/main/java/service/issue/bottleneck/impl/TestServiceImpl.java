package service.issue.bottleneck.impl;

import java.util.UUID;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import service.issue.bottleneck.TestService;


public class TestServiceImpl implements TestService {
	protected Vertx vertx;
	protected JsonObject config;
	
	
	public TestServiceImpl(Vertx vertx, JsonObject config) {
		this.vertx = vertx;
		this.config = config;		
	}
	
	
	@Override
	public TestService test(Handler<AsyncResult<String>> handler){
		testCall().setHandler(handler);
		
		return this;
	}
	
	private Future<String> testCall(){
		Future<String> future = Future.future();
		future.complete(UUID.randomUUID().toString());
		
		return future;
	}
}
