/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.serving;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import restbucks.rest.impl.RestResponse;


@Service
public class ServingControllerSupport {

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

}
