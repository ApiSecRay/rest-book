/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.order;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import restbucks.rest.impl.RestResponse;
import restbucks.rest.serving.ServingResource;


@Service
public class OrderControllerSupport {

  public RestResponse<Void> delete(String orderId) {
    RestResponse<Void> response = new RestResponse<>(null);
    response.setStatus(HttpStatus.NO_CONTENT);
    return response;
  }

  public RestResponse<ServingResource> get(String orderId) {
    ServingResource result = new ServingResource();
    // result.xxx = ...;
    RestResponse<ServingResource> response = new RestResponse<>(result);
    // response.deny(Actions.YYY);
    // response.setStatus(HttpStatus.ZZZ);
    return response;
  }

  public RestResponse<OrderResource> put(String orderId, OrderResource input) {
    OrderResource result = new OrderResource();
    // result.xxx = ...;
    RestResponse<OrderResource> response = new RestResponse<>(result);
    // response.deny(Actions.YYY);
    // response.setStatus(HttpStatus.ZZZ);
    return response;
  }

}
