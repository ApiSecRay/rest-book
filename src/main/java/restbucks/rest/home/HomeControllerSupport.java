/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.home;

import org.springframework.stereotype.Service;

import restbucks.rest.impl.RestResponse;


@Service
public class HomeControllerSupport {

  public RestResponse<HomeResource> get() {
    return new RestResponse<>(new HomeResource());
  }

}
