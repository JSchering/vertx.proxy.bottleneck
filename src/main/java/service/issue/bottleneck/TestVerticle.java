package service.issue.bottleneck;

import java.util.Set;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.serviceproxy.ProxyHelper;
import service.issue.bottleneck.impl.TestServiceImpl;

/**
 * A verticle for the item microservice. This microservice uses the RPC Proxy services for communication and handles all item related services.
 * 
 * @see Modeled from: https://github.com/sczyh30/vertx-blueprint-microservice
 * @author scherijc
 */
public class TestVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(TestVerticle.class);
	protected ServiceDiscovery discovery;
	protected Set<Record> registeredRecords = new ConcurrentHashSet<>();
	
	@Override
	public void start(Future<Void> future) throws Exception {

		// init service discovery instance
		discovery = ServiceDiscovery.create(vertx);		
		for(int i = 0; i < 1; i++){
			// register the service proxy on event bus
			ProxyHelper.registerService(TestService.class, vertx, new TestServiceImpl(vertx, config()), TestService.SERVICE_ADDRESS);
		}

		publishEventBusService(TestService.SERVICE_NAME, TestService.SERVICE_ADDRESS, TestService.class).setHandler(future.completer());
	}

	protected Future<Void> publishEventBusService(String name, String address, Class serviceClass) {
		Record record = EventBusService.createRecord(name, address, serviceClass);
		return publish(record);
	}

	private Future<Void> publish(Record record) {
		if (discovery == null) {
			try {
				start();
			}
			catch (Exception e) {
				throw new IllegalStateException("Cannot create discovery service");
			}
		}

		Future<Void> future = Future.future();
		// publish the service
		discovery.publish(record, ar -> {
			if (ar.succeeded()) {
				registeredRecords.add(ar.result());
				logger.info("Service <" + ar.result().getName() + "> published");
				future.complete();
			}
			else {
				future.fail(ar.cause());
			}
		});

		return future;
	}
}
