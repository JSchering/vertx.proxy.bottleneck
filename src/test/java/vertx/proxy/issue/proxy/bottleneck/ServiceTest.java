package vertx.proxy.issue.proxy.bottleneck;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.serviceproxy.ProxyHelper;
import service.issue.bottleneck.TestService;
import service.issue.bottleneck.TestVerticle;

@RunWith(VertxUnitRunner.class)
public class ServiceTest {

	private static TestService service;

	private static Vertx vertx;
	private static JsonObject config;

	@ClassRule
	public static RunTestOnContext rule = new RunTestOnContext();

	@BeforeClass
	public static void setUpBeforeClass(TestContext context) throws Exception {

		vertx = rule.vertx();

		DeploymentOptions options = new DeploymentOptions();
		vertx.deployVerticle(TestVerticle.class.getName(), options, context.asyncAssertSuccess());

		service = ProxyHelper.createProxy(TestService.class, vertx, TestService.SERVICE_ADDRESS);
	}

	@AfterClass
	public static void tearDownAfterClass(TestContext context) throws Exception {
		vertx.close(context.asyncAssertSuccess());
	}

	@Test
	public void test(TestContext context) {
		final Async async = context.async();

		List<Future> futures = new ArrayList<Future>();
		for (int i = 0; i < 10000; i++) {
			Future future = Future.future();
			futures.add(future);
			long startTime = System.currentTimeMillis();
			service.test(handler -> {
				future.complete();
				long endTime = System.currentTimeMillis();
				System.out.println("TEST TIME:" + (endTime - startTime) + "ms");
			});
		}
		
		CompositeFuture.all(futures).setHandler(handler ->{
			async.complete();
		});
	}
}
