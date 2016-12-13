/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package service.issue.bottleneck.rxjava;

import java.util.Map;
import rx.Observable;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


public class TestService {

  final service.issue.bottleneck.TestService delegate;

  public TestService(service.issue.bottleneck.TestService delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public static TestService create(Vertx vertx, JsonObject config) { 
    TestService ret = TestService.newInstance(service.issue.bottleneck.TestService.create((io.vertx.core.Vertx)vertx.getDelegate(), config));
    return ret;
  }

  public static TestService createProxy(Vertx vertx, String address) { 
    TestService ret = TestService.newInstance(service.issue.bottleneck.TestService.createProxy((io.vertx.core.Vertx)vertx.getDelegate(), address));
    return ret;
  }

  /**
   * Get the version of the currently running microservice
   * @param handler 
   * @return The microservice version
   */
  public TestService test(Handler<AsyncResult<String>> handler) { 
    delegate.test(handler);
    return this;
  }

  /**
   * Get the version of the currently running microservice
   * @return 
   */
  public Observable<String> testObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    test(handler.toHandler());
    return handler;
  }


  public static TestService newInstance(service.issue.bottleneck.TestService arg) {
    return arg != null ? new TestService(arg) : null;
  }
}
